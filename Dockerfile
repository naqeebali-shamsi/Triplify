FROM openjdk:8u111-jdk-alpine
VOLUME /tmp
ADD /target/app-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]