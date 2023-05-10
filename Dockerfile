FROM openjdk:17-jdk-slim
WORKDIR /ms-email
COPY target/email-2.2.1.jar app.jar
CMD ["java", "-jar", "app.jar"]
