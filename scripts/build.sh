#!/bin/bash
JAR_PATH=./build/libs
JAR_NAME=cquec-0.0.1-SNAPSHOT.jar

#cd $JAR_PATH
JAVA_PID=$(pgrep -f ${JAR_NAME})

if [ -z $JAVA_PID ]; then
	echo "프로세스가 실행되고 있지 않습니다."
else
	echo "대상 프로세스 $JAVA_PID를 Kill 처리하였습니다."
	kill -9 $JAVA_PID
	sleep 5
fi

setsid java -jar $JAR_PATH/$JAR_NAME > nohup.out 2>&1 &