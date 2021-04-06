FROM java:8
VOLUME /tmp
ARG JAR_FILE
ADD target/${JAR_FILE} myapp.jar
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/myapp.jar"]