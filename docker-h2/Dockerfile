#Dockerfile H2 database engine
FROM java:7
MAINTAINER AlexandeR "alexander.rogalsky@yandex.ru"
LABEL Description="H2 database engine default repository image" Vendor="WildBeesLabs" Version="1.0"

ARG H2_DB_SOURCE=http://www.h2database.com/h2-2017-06-10.zip
#ENV H2_DB_SOURCE http://www.h2database.com/h2-2017-06-10.zip
ENV BASE_DIR /opt/h2/data

RUN curl $H2_DB_SOURCE -o h2.zip \
    && unzip h2.zip -d /opt/ \
    && rm h2.zip \
    && mkdir -p ${BASE_DIR}

EXPOSE 81 1521

CMD java -cp /opt/h2/bin/h2*.jar org.h2.tools.Server \
 	-web -webAllowOthers -webPort 81 \
 	-tcp -tcpAllowOthers -tcpPort 1521 \
 	-baseDir ${BASE_DIR}