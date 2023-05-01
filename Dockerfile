FROM openjdk:17-jdk-slim
WORKDIR /ms-email
COPY target/email-1.0.0.jar app.jar
CMD ["java", "-jar", "app.jar"]
