#ifndef _EASY_CONNECT_H_
#define _EASY_CONNECT_H_

#include <stdio.h>
#include "ec_config.h"

typedef struct config_pkt {
    int device_id;
    char *password;
    int developer_id;
    char *key;
    int developer_device_id;
} CONFIGPKT;

typedef struct ec_data {
    char code;
    char directoin;
    char msg_type;
    char level;
    char *msg;
    int device_id;
    int user_id;
    long time;

    int all;
    int index;
    int sign;
    int pkt_num;
} ECDATA;


/**
 * @brief  remember free the *msg when finished
 *
 * @param
 * @param msg
 *
 * @return
 */
typedef void (* deal)(const int sender, const char *msg, const char *config);

/**
 * @brief ec_init init the ec
 */
void ec_init(deal d);

/**
 * @brief ec_send
 *
 * @param dst
 * @param data
 * @param data_size
 * @param type, 1 udp 2 tcp
 *
 * @return
 */
ssize_t ec_send(int dst, char msg_type, char level, const char *data,
        int data_size, char type);
//void ec_recv(deal d);

/**
 * @brief ectcp_send send data using tcp
 *
 * @param reciver
 * @param data
 * @param size
 *
 * @return send data size
 */
//size_t ec_send_tcp(int reciver, const char *data, size_t size);

/**
 * @brief ecudp_recv recv msg by udp, default start
 *
 * @param d
 */
//void ecudp_recv(deal d);

/**
 * @brief ectcp_recv
 *
 * @param d
 */
//void ectcp_recv(deal d);

#endif /* ifndef _EASY_CONNECT_H_ */
