FROM openjdk:17-jdk
WORKDIR /app
COPY build/libs/tbankApp.jar app.jar
CMD ["java", "-jar", "my-app.jar"]