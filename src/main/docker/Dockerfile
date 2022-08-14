FROM openjdk:8u212-jre-alpine3.9
VOLUME /tmp
ADD testdocker-0.0.1-SNAPSHOT.jar myapp.jar
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/myapp.jar"]