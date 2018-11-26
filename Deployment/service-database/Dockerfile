FROM mysql:5.7.24

COPY sql/init.sql /docker-entrypoint-initdb.d

CMD ["mysqld"]