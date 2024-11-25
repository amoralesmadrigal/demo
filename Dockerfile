#
# Build stage
#
FROM maven:3.8.3-openjdk-17-slim AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM eclipse-temurin:17-jre-alpine
#ARG JAR_FILE=target/*.jar
COPY --from=build ./target/demo-0.0.1-SNAPSHOT.jar app.jar

# Ejecuta con opciones de JVM optimizadas
CMD java \
    -XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=70.0 \
    -XX:InitialRAMPercentage=30.0 \
    -XX:MinRAMPercentage=15.0 \
    -XX:+UnlockExperimentalVMOptions \
    -XX:+UseG1GC \
    -XX:MaxGCPauseMillis=200 \
    -XX:ParallelGCThreads=1 \
    -Djava.security.egd=file:/dev/./urandom \
    -jar app.jar
	
# ENV PORT=8080
EXPOSE 8080

#ENTRYPOINT [ "java", "-jar", "/app.jar"]
