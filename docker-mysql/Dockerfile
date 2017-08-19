#Dockerfile MySQL database engine
FROM ubuntu:latest
MAINTAINER AlexandeR "alexander.rogalsky@yandex.ru"

ENV MYSQL_USER=mysql \
    MYSQL_DATA_DIR=/var/lib/mysql \
    MYSQL_RUN_DIR=/var/run/mysqld \
    MYSQL_LOG_DIR=/var/log/mysql
	
RUN apt-get update && \
	DEBIAN_FRONTEND=noninteractive apt-get -y install mysql-server && \
	mkdir -p ${MYSQL_DATA_DIR} && \
	mkdir -p ${MYSQL_RUN_DIR} && \
	mkdir -p ${MYSQL_LOG_DIR} && \
	chown -R mysql:mysql ${MYSQL_DATA_DIR} && \
	chown -R mysql:mysql ${MYSQL_RUN_DIR} && \
	chown -R mysql:mysql ${MYSQL_LOG_DIR}
#	&& rm -rf /var/lib/apt/lists/*

# Default UTF-8 character set and address binding
RUN sed -i -e "$ a [client]\n\n[mysql]\n\n[mysqld]"  /etc/mysql/my.cnf && \
	sed -i -e "s/\(\[client\]\)/\1\ndefault-character-set = utf8/g" /etc/mysql/my.cnf && \
	sed -i -e "s/\(\[mysql\]\)/\1\ndefault-character-set = utf8/g" /etc/mysql/my.cnf && \
	sed -i -e "s/\(\[mysqld\]\)/\1\ninit_connect='SET NAMES utf8'\ncharacter-set-server = utf8\ncollation-server=utf8_unicode_ci\nbind-address = 0.0.0.0/g" /etc/mysql/my.cnf

COPY ./start.sh /sbin/start.sh
RUN chmod +x /sbin/start.sh
ENTRYPOINT ["/sbin/start.sh"]
EXPOSE 3306
VOLUME ["${MYSQL_DATA_DIR}", "${MYSQL_RUN_DIR}"]

CMD [ "/usr/bin/mysqld_safe" ]