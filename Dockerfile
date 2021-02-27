FROM openjdk:8-jre-alpine
WORKDIR /opt/beer-api
ARG JAR_FILE
COPY ${JAR_FILE} /opt/beer-api/beer-api.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","beer-api.jar"]