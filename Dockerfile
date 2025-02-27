FROM eclipse-temurin:17-jre-alpine

COPY ./src src/
COPY ./pom.xml pom.xml

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
COPY --from=builder target/*.jar jobboard-1.0.jar
EXPOSE 8080
CMD ["java","-jar","jobboard-1.0.jar"]
