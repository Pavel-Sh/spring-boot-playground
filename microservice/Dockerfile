FROM openjdk:15-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
RUN apk add --update netcat-openbsd && rm -rf /var/cache/apk/*