#ifndef _CONFIG_H_
#define _CONFIG_H_

#include <stdio.h>

//open debug?
#define DEBUG   1
#define True    1
#define False   0

//define the server address and ther server port
//#define SERVERADDR      "10.0.0.9"

//#define SERVERADDR      "192.168.137.208"
#define SERVERADDR      "192.168.137.1"
//#define SERVERADDR      "192.168.43.123"
#define SERVERPORT      9003

// the heart inerval, unit is seonds
#define HEARTINT        10

#if DEBUG
#define debug(x) \
{fprintf(stdout, "File: \"%s\", Func: [%s], Line(%d), Inf: %s\n", \
        __FILE__, __FUNCTION__, __LINE__, x);}
#else
#define debug(X)
#endif

typedef enum msg_type {
    msg = 1, object
} MTYPE;

typedef enum level {
    normal = 1, important, urgent
} LEVEL;

typedef struct question {
    int sender;
    char *question; // commond
    char *answer;   // config
} QUESTION;

#endif /* ifndef _CONFIG_H_ */
