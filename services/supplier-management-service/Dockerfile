FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/supplier-management-service-*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]