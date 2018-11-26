FROM mysql:8.0.13

COPY sql/init.sql /docker-entrypoint-initdb.d

CMD ["mysqld"]