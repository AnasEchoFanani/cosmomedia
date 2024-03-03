# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Copy the JAR file from the build stage to the final image
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar cosmomedia.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "cosmomedia.jar"]
