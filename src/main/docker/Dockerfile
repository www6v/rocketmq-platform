FROM 10.4.65.226/linux/centos:ssh-7

VOLUME /tmp
ADD rocketmq-compute.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "source /etc/profile; java $JAVA_OPTS -jar /app.jar" ]
