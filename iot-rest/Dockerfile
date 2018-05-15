FROM java:8-jre
WORKDIR /iot

ARG JAR_FILE

COPY ${JAR_FILE}  ${WORKDIR}/app.jar
COPY target/application*.properties /${WORKDIR}/

VOLUME /log
VOLUME /static
VOLUME /config

EXPOSE 8080 8081 8082 8083

CMD ["java","-jar","-Duser.timezone=GMT+08","rest.jar","--spring.config.location=/config/"]