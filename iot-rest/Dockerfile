FROM java:8-jre
WORKDIR /iot

ARG JAR_FILE
ARG CONFIG_FILE

COPY ${JAR_FILE}  /iot/app.jar
COPY ${CONFIG_FILE} /etc/iot

EXPOSE 8080

VOLUME /log
VOLUME /static
VOLUME /config

#资源上载目录
VOLUME /repo


#配置文件从/config/读取，日志存储在/log/iot.log中，注意将/config和/log映射到合适的位置
ENTRYPOINT ["java","-jar","-Duser.timezone=GMT+08","app.jar","--spring.config.location=/config/","--logging.file=/log/iot.log"]