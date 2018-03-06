#include <pthread.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdio.h>

#include "ec.h"
#include "ec_config.h"
#include "ec_question.h"
#include "cJSON.h"

/**
 * 发送者的地址信息
 * 只有这里面的人才会给他发送相应，如果不存在这里面的话那么就发送给服务器
 * 主要是为了局域网通信
 */
typedef struct sender {
    int sender_id;
    struct sockaddr_in sender_addr;
    char type;  // tcp or udp
    int time;   // 设置超时时间
} SENDER;

SENDER senders[64];     // users addr
int senders_num = 0;     // 接受到的发送者的数量

/**********************************************************************/


/**
 * @brief 最多能够有8位有问题的用户
 */
#define questioners_num 8    // 问题用户数最大数量
static QUESTION questioners[questioners_num];     // 最多8位用户


// to get udp and tcp socket
int __ec_tcp(const char *host, const int port);
int __ec_udp(const char *host, const int port);

char * __ec_read_config(const char *path);

static int __ec_udp_sock = 0;  //udp socket
static int __ec_tcp_sock = 0;  //tcp socket

static struct sockaddr_in __server_addr;   // the server addr

static ECDATA __ec_data;
static CONFIGPKT __config_pkt;
static char *__heart_str = NULL; //heart str

/**
 * @brief get_sender
 *  get the sender
 *
 * @param id
 *
 * @return
 */
static SENDER __ec_get_sender(int id) {
    if (id == 0) return senders[0];
    int i = 1;

    for (; i < senders_num; i++) {
        if (senders[i].sender_id == id) {
            return senders[i];
        }
    }

    return senders[0];
}

/**
 * @brief __ec_is_a_question
 *  判断是不是问题
 *
 * @param msg
 *
 * @return
 */
static signed char __ec_is_a_question(char *msg) {
    debug(msg);
    int i = 0;

    for (; i < ALLQUESTIONS; i++) {
        debug(QUESTIONS[i].question);
        debug(QUESTIONS[i].answer);

        // 如果发现是一个问题，那么返回真
        if (!strcmp(QUESTIONS[i].question, msg)) {
            debug("found queston");
            return i;
        }
    }

    return -1;
}

/**
 * @brief __ec_get_question
 *  调用者首先应该判断是否存在question，如果存在的话，那么就说明这是一个命令，
 *  后面跟着参数，当然也可能不跟，所以可以直接发送给开发者
 *  如果不存在的话，那么就是问题，可以发送给开发者也可以不发送
 *
 * @param sender
 * @param msg
 *
 * @return
 */
static QUESTION __ec_get_question(int sender, char *msg) {
    debug(msg);
    QUESTION question;
    question.sender = sender;
    int question_id = __ec_is_a_question(msg);
    printf("the question_id is %d\n", question_id);

    // 如果是问题
    if (question_id >= 0) {
        debug("it is a question");
        // 那么问题也就是commond设置为空
        question.question = 0;
        // 参数设置为问题
        question.answer = QUESTIONS[question_id].answer;
        debug(question.answer);

        // 添加到questioners中
        int i = 0;
        debug("add to questions");
        for (; i < questioners_num; i++) {
            if (questioners[i].sender == 0) {

                questioners[i].sender = sender;
                questioners[i].question = msg;
                questioners[i].answer = 0;

                break;
            }
        }
        printf("the index is %d\n", i);

        if (i >= questioners_num) {
            free(msg);
            debug("error");
            question.answer = (char *)ERRMSG;
        }

    } else {
        debug("not a question");
        // 不是问题, 也就是说直接就是命令，另外还需要判断是不是回答
        // 那么可以直接发送给开发者了
        int i = 0;
        for (; i < questioners_num; i++) {
            if (questioners[i].sender == sender) {
                // 是一个回答
                debug("is an answer");
                question = questioners[i];
                question.answer = msg;

                // 将回答删除
                questioners[i].sender = 0;
                questioners[i].question = 0;
                questioners[i].answer = 0;

                return question;
            }
        }

        debug("it is a commong");
        question.question = msg;    // commond
        question.answer = 0;        // config
    }

    return question;
}

/**
 * @brief __ec_deal_recv
 *  deal the recv data
 *
 * @param d
 */
static void __ec_deal_recv(const char *str, deal d) {
    debug(str);
    cJSON *root = cJSON_Parse(str);

    if (root) {
        debug("root is not null");
    } else {
        debug("root is null");
    }

    char *msg;
    QUESTION question;

    if (cJSON_GetObjectItem(root, "code")) {
        debug("i get the code");
    } else {
        debug("code error");
    }

    if (cJSON_GetObjectItem(root, "code")->valueint == 2) {
        // 0 || 1 不是机器发的就行了，可能是服务器发送的，可能是用户发送的
        if (cJSON_GetObjectItem(root, "direction")->valueint == 0 ||
                cJSON_GetObjectItem(root, "direction")->valueint == 1) {
            debug("");

            if (cJSON_GetObjectItem(root, "msg")) {
                debug("");
                char *s = cJSON_GetObjectItem(root, "msg")->valuestring;
                debug("");

                // 首先分配一块空间
                msg = (char *)malloc(strlen(s)+1);
                memcpy(msg, s, strlen(s));
                msg[strlen(s)] = 0;
                debug("");

                // 获取question
                question =
                    __ec_get_question(cJSON_GetObjectItem(root, "userId")->valueint, msg);
                debug(question.question);
                debug(question.answer);

                // 这是一个问题,需要请求参数
                if (!question.question) {
                    debug("");
                    // 发送请求参数
                    // question 的第二个参数就是需要发送给用户的问题, 暂时只有udp吧
                    ec_send(question.sender, 1, 3, question.answer,
                            strlen(question.answer), 0);
                    debug("");
                } else {
                    debug("+++++++++++++++++++++++++");
                    debug(question.question);
                    debug(question.answer);
                    debug("+++++++++++++++++++++++++");

                    d(question.sender, question.question, question.answer);
					free(question.question);

                    debug("");
                }
            }
        }
    }

    debug("");
    cJSON_Delete(root);
}

/**
 * @brief __ec_recv_udp
 *  recv data from the dst then set dst addr
 *
 * @param data
 *
 * @return
 */
static void *__ec_recv_udp(void *data) {
    pthread_detach(pthread_self());
    deal d = (deal)data;

    char buf[1024];
    struct timeval timeout;
    fd_set set;

    /*    debug("will recv");*/
    while (1) {

        debug("in recving while begin");
        // reset time out
        timeout.tv_sec = 10;
        timeout.tv_usec = 0;

        //FD_ZERO(&rd);  // clear
        FD_SET(__ec_udp_sock, &set);  // add udp to select
        int ret = select(__ec_udp_sock + 1, &set, NULL, NULL, &timeout);

        debug("select has event or timeout");
        if (ret <= 0) {
            debug("select has no event");
            continue;
        } else {
            debug("select has events");

            if (FD_ISSET(__ec_udp_sock, &set)) {
                debug("__ec_udp_sock an read");
                int data_len;
                int len = sizeof(struct sockaddr_in);

                if ((data_len = recvfrom(__ec_udp_sock, buf, sizeof(buf), 0,
                                //(struct sockaddr *)&senders[0].sender_addr, (socklen_t *)&len)) > 0) {
                    (struct sockaddr *)&__server_addr, (socklen_t *)&len)) > 0) {

                        buf[data_len] = 0;
                        debug("recved one pkt");
                        debug(buf);
                        __ec_deal_recv(buf, d);
                    }
                //debug(buf);
            }

            }
            debug("restart select");
        }

        return 0;
    }

/**
	* @brief __ec_data_to_json_char
	*
	* @param ed
	*
	* @return
	*/
static char * __ec_data_to_json_char(ECDATA ed) {
	cJSON *root = NULL;

	root = cJSON_CreateObject();
	if (root == NULL) {
		return NULL;
	}

	cJSON_AddNumberToObject(root, "code", ed.code);
	cJSON_AddNumberToObject(root, "direction", ed.directoin);
	cJSON_AddNumberToObject(root, "deviceId", ed.device_id);
	cJSON_AddNumberToObject(root, "userId", ed.user_id);
	cJSON_AddNumberToObject(root, "msgType", ed.msg_type);
	cJSON_AddNumberToObject(root, "time", ed.time);
	cJSON_AddNumberToObject(root, "level", ed.level);
	cJSON_AddStringToObject(root, "msg", ed.msg);

	cJSON_AddNumberToObject(root, "all", ed.all);
	cJSON_AddNumberToObject(root, "index", ed.index);
	cJSON_AddNumberToObject(root, "sign", ed.sign);
	cJSON_AddNumberToObject(root, "num", ed.pkt_num);

	char *str = cJSON_PrintUnformatted(root);

	free(ed.msg);
	cJSON_Delete(root);
	debug("");
	debug(str);

	return str;
}

/**
	* @brief __ec_create_ec_data
	*
	* @param dst
	* @param msg_type
	* @param level
	* @param msg
	* @param len
	*
	* @return
	*/
static ECDATA __ec_create_ec_data(int dst, char msg_type, char level,
		const char *msg, int len) {
	ECDATA ec_data;
	ec_data.code = 2;
	ec_data.directoin = 2;
	ec_data.msg_type = msg_type;
	ec_data.level = level;

	ec_data.msg = (char *)malloc(len+1);
	memcpy(ec_data.msg, msg, len);
	ec_data.msg[len] = 0;

	ec_data.user_id = dst;
	ec_data.device_id = __config_pkt.device_id;
	ec_data.time = time(0)*1000;

	ec_data.all = 0;
	ec_data.index = 0;
	ec_data.sign = 0;
	ec_data.pkt_num = 0;

	return ec_data;
}

/**
	* @brief __ec_get_heart_pkt
	*
	* @param str
	*
	* @return
	*/
static CONFIGPKT __ec_get_config_pkt(cJSON *root) {
	CONFIGPKT cfkt;

	if (!root) {
		debug("json create error");
		exit(-1);
	}

	cJSON *item = NULL;

	cfkt.device_id = cJSON_GetObjectItem(root, "deviceId")->valueint;
	cfkt.developer_id = cJSON_GetObjectItem(root, "developerId")->valueint;

	item = cJSON_GetObjectItem(root, "password");
	cfkt.password = (char *)malloc(strlen(item->valuestring));
	memcpy(cfkt.password, item->valuestring, strlen(item->valuestring));

	item = cJSON_GetObjectItem(root, "key");
	cfkt.key = (char *)malloc(strlen(item->valuestring));
	memcpy(cfkt.key, item->valuestring, strlen(item->valuestring));

	item = cJSON_GetObjectItem(root, "developerDeviceId");
	cfkt.developer_device_id = item->valueint;

	return cfkt;
}

/**
	* @brief __ec_exit
	*  the exit function
	*
	* @param sig_num
	*/
static void __ec_exit(int sig_num) {
	if (sig_num) debug("ok");
	if (__heart_str) free(__heart_str);
}


/**
	* @brief __ec_start_heart_beat start heart
	*
	* @param data
	*
	* @return
	*/
static void * __ec_start_heart_beat(void *data) {
	pthread_detach(pthread_self());
	int len = strlen(__heart_str);
	//struct sockaddr_in dst_addr = *(struct sockaddr_in *)data;

	while (1) {
		debug(__heart_str);
		if (sendto(__ec_udp_sock, __heart_str, len, 0, (struct sockaddr *)data,
					sizeof(struct sockaddr_in)) < 0) {

			debug("heart error");
		} else
			debug("send success");
		sleep(HEARTINT);
	}

	return 0;
}

/**
	* @brief __ec_send
	*
	* @return
	*/
static ssize_t __ec_send(int sock, char *data, int size, struct sockaddr_in addr) {
	int n = 0;
	int send_n = 0;

	debug("will send data")
		while (n < size) {
			printf(".");
			send_n = sendto(sock, data + n, size - n, 0,
					//(struct sockaddr *)&addr, sizeof(struct sockaddr_in));
					(struct sockaddr *)&__server_addr, sizeof(struct sockaddr_in));
			if (send_n < 0) {
				debug("send error");
				return -1;
			}
			n += send_n;
		}
	debug("send success");
	return send_n;
}

/**
	* @brief ec_init
	* init
	*/
void ec_init(deal d) {
	debug("");
	// get the udp socket
	__ec_udp_sock = __ec_udp(SERVERADDR, SERVERPORT);
	//struct sockaddr_in server_addr;

	// judge the socket
	if (__ec_udp_sock <= 0) {
		debug("create udp socket error");
		exit(-1);
	}

	debug("");
	// get the heart str
	__heart_str = __ec_read_config("config.json");
	debug("");

	if (!__heart_str) {
		// 这里应该添加如果文件损坏读取不了的话应该怎么处理的内容
		// 比如前后台商定好出现这种情况应该传输什么数据等
		debug("read config error");
		// 统一执行一个退出函数
		exit(-1);
	}

	cJSON *root = cJSON_Parse(__heart_str);
	cJSON_AddNumberToObject(root, "code", 1);
	cJSON_AddNumberToObject(root, "direction", 2);
	free(__heart_str);
	__heart_str = cJSON_PrintUnformatted(root);
	debug(__heart_str);
	__config_pkt = __ec_get_config_pkt(root);
	cJSON_Delete(root);

	memset(&__server_addr, 0, sizeof(__server_addr));
	__server_addr.sin_family = AF_INET;
	__server_addr.sin_port = htons(SERVERPORT);

	SENDER sender;
	sender.sender_id = 0;
	sender.type = 0;
	sender.sender_addr = __server_addr;
	senders[0] = sender;
	senders_num++;

	if (!inet_aton(SERVERADDR, &__server_addr.sin_addr)) {
		debug("translate the address to binary failed");
		//是不是应该先关闭socket，然后释放msg啊？这里应该统一执行一个推出函数
		exit(-1);
	}

	// start heart beart
	pthread_t heart_tid;
	pthread_create(&heart_tid, NULL, __ec_start_heart_beat,
			//(void *)&senders[0].sender_addr);
		(void *)&__server_addr);

	// start recv
	pthread_t recv_tid;
	pthread_create(&recv_tid, NULL, __ec_recv_udp, (void *)d);

	debug("");
}

/**
	* @brief ec_send
	*
	* @param dst
	* @param data
	* @param data_size
	* @param type
	*
	* @return
	*/
ssize_t ec_send(int dst, char msg_type, char level, const char *data,
		int data_size, char method) {

	printf("the sender is %d\n", dst);
	SENDER sender = __ec_get_sender(dst);

	ssize_t s = 0;
	ECDATA ec_data = __ec_create_ec_data(dst, msg_type, level, data, data_size);

	char *str = __ec_data_to_json_char(ec_data);
	debug("deall ec data");
	debug(str);

	switch (method) {
		// udp
		case 0:
			{
				//s = __ec_send(__ec_udp_sock, str, strlen(str), sender.sender_addr);
				s = __ec_send(__ec_udp_sock, str, strlen(str), __server_addr);
				debug("send success");
				break;
			}
		case 1:
			{
				break;
			}

		default:
			break;
	}
	free(str);
	return s;
}

