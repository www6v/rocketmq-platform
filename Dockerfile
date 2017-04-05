FROM 10.4.65.226/linux/centos:ssh-7

ADD target/rocketmq-compute.jar /usr/local/app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "source /etc/profile; java $JAVA_OPTS -jar /app.jar" ]
