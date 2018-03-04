#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <pthread.h>

#include <wiringPi.h>

#include "ec.h"
#include "ec_config.h"

static int sender = 0;
MTYPE mtype = msg;
LEVEL level = normal;

int i = 25;

const char *success = "operation success\0";
const char *failed = "unknow operation\0";

int mode = 0;
int eval = 0;

int current_tmp = 0;
int alert_tmp = 50000; //30 Centigrade

FILE *fp;

int get_tempeture() {
    char buf[64];
    memset(buf, 0, 64);
    fp = popen("cat /sys/class/thermal/thermal_zone0/temp", "r");
    fgets(buf, sizeof(buf), fp);
    return atoi(buf);
}


void deal_data(int s, char *commond, char *config) {
    sender = s;
    debug(commond);

    if (!strcmp(commond, "0") || !strcmp(commond, "close")) {
        printf("---------------------------\n");
        debug("open");
        printf("---------------------------\n");

        mode = 0;
        ec_send(s, 1, 3, success, strlen(success), 0);
    } else if (!strcmp(commond, "1") || !strcmp(commond, "open")) {
        mode = 1;
        ec_send(s, 1, 3, success, strlen(success), 0);
    } else if (!strcmp(commond, "2") || !strcmp(commond, "flash")) {
        if (config) {
            mode = 2;
            eval = atoi(config);
            ec_send(s, 1, 3, success, strlen(success), 0);
        } else {
            ec_send(s, 1, 3, failed, strlen(failed), 0);
        }
    } else if (!strcmp(commond, "3")) {
        char send_buf[128];
        memset(send_buf, 0, 128);
        sprintf(send_buf, "current tempeture: %d", current_tmp);
        ec_send(s, 1, 3, send_buf, strlen(send_buf), 0);
    } else if (!strcmp(commond, "4")) {
        if (config) {
            alert_tmp = atoi(config) * 1000;
            ec_send(s, 1, 3, success, strlen(success), 0);
        } else {
            ec_send(s, 1, 3, failed, strlen(failed), 0);
        }
    } else {
        ec_send(s, 1, 3, failed, strlen(failed), 0);
    }

    free(commond);
    free(config);
}

void *tempture(void *t) {
    if (t) printf("");
    char send_buf[128];

    while(1) {
        delay(5000);
        current_tmp = get_tempeture();
        if ((current_tmp > alert_tmp)) {
            debug("~~~~~~~~~~~~~~~~~~太热啦")
                memset(send_buf, 0, 128);
            sprintf(send_buf, "[alert:]\n current tempture is %.2f℃\nstandared is %d  ℃",
                    (float)current_tmp/1000, alert_tmp/1000);
            ec_send(0, 1, 3, send_buf, strlen(send_buf), 0);
        }
    }
}

void cexit() {
    pclose(fp);
}

int main() {
    wiringPiSetup();
    pinMode(i, OUTPUT);
    ec_init(deal_data);
    pthread_t tid;
    pthread_create(&tid, NULL, (void *)tempture, NULL);

    while(1) {
        switch(mode) {
            case 0:
                digitalWrite(i, LOW);
                break;
            case 1:
                digitalWrite(i, HIGH);
                break;
            case 2:
                digitalWrite(i, LOW);
                delay(eval);
                digitalWrite(i, HIGH);
                delay(eval);
                break;
        }
    }
}

