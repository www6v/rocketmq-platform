FROM 10.4.65.226/linux/centos:7.1

ADD target/rocketmq-compute.jar /usr/local/app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /usr/local/app.jar" ]
