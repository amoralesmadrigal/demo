#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY --from=build ./target/demo-0.0.1-SNAPSHOT.jar app.jar

# ENV PORT=8080
EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/app.jar"]
