FROM openjdk:17-jdk-slim
WORKDIR /ms-email
COPY target/email-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
