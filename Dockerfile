# Build stage
FROM maven:3.8.1-jdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Package stage
FROM openjdk:17-jdk-slim
COPY --from=build /target/*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","app.jar"]

