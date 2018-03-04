
#include <string.h>
#include <stdio.h>

#include "ec.h"

int __ec_sock(const char *host, const int port, const char *transport);

/**
 * @brief ec_udp 连接指定的ip和端口，返回udp连接的socket
 *
 * @param host		- 主机ip 或者 主机名称
 * @param port		- 主机端口
 *
 * @return
 */
int __ec_udp(const char *host, const int port)
{
    debug("ec udp connect");
    printf("主机 %s， 服务: %d\n", host, port);
    return __ec_sock(host, port, "udp");
}
