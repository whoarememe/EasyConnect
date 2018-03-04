#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#include "ec_config.h"

/**
 * @brief __ec_open_file
 *  get the file description, if something error, it will return -1
 *
 * @param path
 *
 * @return
 */
static int __ec_open_file(const char *path) {
    debug("");
    int fd = -1;

    if (!path) {
        debug("the path is null");
    } else {
        if ((fd = open(path, O_RDONLY)) == -1) {
            debug("open file error");
        }
    }

    debug("");
    return fd;
}

/**
 * @brief read_ec_config
 *  read the ec config.json file
 *  if something error, it will return NULL
 *  remember free the char * when finished
 *
 * @return
 */
char * __ec_read_config(const char *path) {
    debug("read config content");
    int fd, len;
    char *buf = NULL;

    if (path) {
        fd = __ec_open_file(path);
        if (fd > 0) {
            // go to the end and get the file size
            len = lseek(fd, 0, SEEK_END);

            if (len > 0) {
                // go to the start
                lseek(fd, 0, SEEK_SET);
                buf = (char *)malloc(len+1);

                // read the content from the file to buf
                if ((len = read(fd, buf, len)) < 0) {
                    free(buf);
                    buf = NULL;
                }
            }
        }
    }

    close(fd);
    debug("");
    debug(buf);
    return buf;
}

/**
 * @brief write_ec_config
 * write info to ec config.json file
 *
 * @return
 */
size_t __ec_write_config(const int device_id, const char *password,
        const int developer_id, const char *key, const char *device_type) {

    return 0;
}
