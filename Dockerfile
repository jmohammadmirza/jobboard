# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Package stage
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/jobboard-2.0.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]

