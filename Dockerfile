FROM 10.4.65.226/linux/centos:ssh-7

ADD target/rocketmq-compute.jar /usr/local/app.jar
ADD arcompute_intranet.crt /usr/local/arcompute_intranet.crt

RUN source /etc/profile \
     && keytool -importcert -alias arcompute_intranet -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit -noprompt -file /usr/local/arcompute_intranet.crt


ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "source /etc/profile; java $JAVA_OPTS -jar /usr/local/app.jar; tail -f /dev/null" ]
