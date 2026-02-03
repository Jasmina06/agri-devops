FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/logistics-distribution-service-*.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]