FROM openjdk:8u212-jre-alpine3.9
VOLUME /tmp
ARG JAR_FILE
ADD target/${JAR_FILE} myapp.jar
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/myapp.jar"]