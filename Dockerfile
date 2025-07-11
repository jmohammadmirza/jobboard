# Build stage
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Package stage
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/jobboard-2.0.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","app.jar"]
