FROM 10.4.65.226/linux/centos:7.1

VOLUME /tmp
ADD rocketmq-compute.jar app.jar
RUN sh -c 'touch /app.jar'
RUN source /etc/profile
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]
