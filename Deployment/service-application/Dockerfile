FROM tomcat:9.0.8-jre10

ADD tomcat-users.xml /usr/local/tomcat/conf/
ADD context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml

COPY webapps/*.war /tmp/
RUN \
    echo "deploy webapps"; \
    mv /tmp/*.war /usr/local/tomcat/webapps;

COPY start-app.sh /root/start-app.sh

CMD ["/root/start-app.sh"]
