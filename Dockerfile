FROM 10.4.65.226/linux/centos:ssh-7

ADD target/rocketmq-compute.jar /usr/local/app.jar
ADD arcompute.crt /usr/local/arcompute.crt

RUN source /etc/profile \
     && keytool -importcert -alias arcompute -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass 123654 -noprompt -file /usr/local/arcompute.crt


ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "source /etc/profile; java $JAVA_OPTS -jar /usr/local/app.jar; tail -f /dev/null" ]
