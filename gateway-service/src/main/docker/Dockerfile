FROM openjdk:8-jdk-alpine

LABEL maintainer="peter@westlin.nu"

ENV JAVA_OPTS ${JAVA_OPTS}

ARG JAR_FILE
COPY ${JAR_FILE} /app.jar

CMD java ${JAVA_OPTS} \
    -XX:+CrashOnOutOfMemoryError \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=/tmp \
	-Djava.security.egd=file:/dev/./urandom \
	-jar /app.jar