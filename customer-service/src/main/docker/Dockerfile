FROM openjdk:8-jdk-alpine
#FROM azul/zulu-openjdk-alpine:12

LABEL maintainer="peter@westlin.nu"

ARG PORT=3001

ENV JAVA_OPTS ${JAVA_OPTS}
ENV SERVER_PORT ${PORT}

EXPOSE ${PORT}

ARG JAR_FILE
COPY ${JAR_FILE} /app.jar

CMD java ${JAVA_OPTS} \
    -XX:+CrashOnOutOfMemoryError \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=/tmp \
	-Djava.security.egd=file:/dev/./urandom \
	-jar /app.jar