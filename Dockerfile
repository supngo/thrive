FROM openjdk:17-jdk-alpine
LABEL maintainer="thongo5430@gmail.com"
VOLUME /tmp
EXPOSE 8080
ADD /target/properties-1.0.0.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]