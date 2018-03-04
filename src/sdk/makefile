
DIR_INC = ./include
DIR_SRC = ./src
DIR_OBJ = ./obj
DIR_BIN = ./bin

SRC = $(wildcard ${DIR_SRC}/*.c)
OBJ = $(patsubst %.c, ${DIR_OBJ}/%.o, ${notdir ${SRC}})

TARGET = ec

CC = gcc
CFLAGSO = -lm -lpthread -lwiringPi
CFLAGSC = -g -Wall -I${DIR_INC}

ALL: ${TARGET}
	mv ./ec ./bin
	mv ./config/config.json ./bin

${TARGET}:	${OBJ}
	$(CC) $(OBJ) $(CFLAGSO)  -o $@

${DIR_OBJ}/%.o: ${DIR_SRC}/%.c
	$(CC) $(CFLAGSC) -c  $< -o $@

.PHONY:clean
clean:
	#find ${DIR_OBJ} -name *.o -exec rm -rf {}
	rm -rf ${DIR_INC}/*.gch
	rm -rf ${DIR_OBJ}/*.o
	rm -rf ${DIR_BIN}/*
