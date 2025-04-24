# Stage 1: Build
FROM openjdk:21-jdk-slim AS build
WORKDIR /app
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
RUN chmod +x ./gradlew
COPY src ./src
# Build the application JAR
RUN ./gradlew bootJar --no-daemon

# Stage 2: Runtime
FROM openjdk:21-slim
WORKDIR /app
# Copy only the built JAR from the build stage
COPY --from=build /app/build/libs/tbankApp-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]