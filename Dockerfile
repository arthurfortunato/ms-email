FROM adoptopenjdk:11-jre-hotspot
WORKDIR /ms-email
COPY target/email-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
