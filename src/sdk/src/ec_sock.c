/* connectsock.c - connectsock */

#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>

#include <netinet/in.h>
#include <arpa/inet.h>

#include <netdb.h>
#include <string.h>
#include <stdlib.h>
#include <errno.h>

#include "ec_config.h"

#ifndef INADDR_NONE
#define INADDR_NONE 0xffffffff
#endif /* INADDR_NONE */

/** int	err_exit(const char *format, ...); */

/**
 * @brief connect_sock
 *
 * @param host
 * @param service
 * @param transport
 *
 * @return
 */
int __ec_sock(const char *host, const int port, const char *transport)
{
    debug("create socket");
    struct hostent *phe;
    struct protoent *ppe;
    struct sockaddr *saddr;
    struct sockaddr_in sin;
    int s, domain, type, proto, size;

    memset(&sin, 0, sizeof(sin));
    sin.sin_family = AF_INET;
    sin.sin_port = port;

    if ((phe = gethostbyname(host)))
        memcpy(&sin.sin_addr, phe->h_addr, phe->h_length);
    else if ((sin.sin_addr.s_addr = inet_addr(host)) == INADDR_NONE)
        return -1;

    // Map transport protocol name to protocol number
    if ((ppe = getprotobyname(transport)) == 0)
        return -1;

    saddr = (struct sockaddr *)&sin;
    domain = AF_INET;
    proto = ppe->p_proto;
    size = sizeof(sin);

    if (strcmp(transport, "udp") == 0) {
        type = SOCK_DGRAM;
        // Allocate a socket
        s = socket(domain, type, proto);
        if (s < 0) {
            debug("create socket error");
            return -1;
        }
    }
    else {
        type = SOCK_STREAM;
        s = socket(domain, type, proto);
        if (s < 0) {
            debug("create socket error");
            return -1;
        }

        // Connect the socket
        if (connect(s, saddr, size) < 0)
        {
            return -1;
        }

    }

    return s;
}
