#ifndef __EC_QUESTOIN__
#define __EC_QUESTOIN__

#include "ec_config.h"

#define ALLQUESTIONS   2

// 凡是收到question中的数据，ec会自动获取下一条参数，
// 然后返回给开发者一个QUESTION
// questions名称请不要动
const QUESTION QUESTIONS[ALLQUESTIONS] = {
    {0, "2", "please input time(ms)\0" },
    {0, "flash", "please input time(ms)\0"}
};

const char * ERRMSG = "出现问题, 请稍候再试\0";

#endif /* ifndef __EC_QUESTOIN__ */
