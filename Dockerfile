FROM java:8-jdk-alpine
COPY ./target/test-containers-example-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "test-containers-example-0.0.1-SNAPSHOT.jar"]