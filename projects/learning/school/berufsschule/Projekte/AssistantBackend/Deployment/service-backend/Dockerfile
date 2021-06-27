FROM tomcat:8

COPY webapps/*.war /tmp/
RUN \
    echo "deploy webapps"; \
    mv /tmp/*.war /usr/local/tomcat/webapps;

COPY ./start-app.sh /root/start-app.sh

CMD ["/root/start-app.sh"]
