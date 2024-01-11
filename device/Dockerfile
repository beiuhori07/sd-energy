# Stage 1: Build the application
FROM gradle:7.6-jdk17 as build

# Set the working directory in the Docker image
WORKDIR /app

# Copy the Gradle configuration files first (for better layer caching)
COPY build.gradle settings.gradle /app/

# Copy the source code
COPY src /app/src

# Build the JAR file
RUN gradle build --no-daemon -x test --parallel

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim

# Set the working directory in the Docker image
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Run the JAR file
ENTRYPOINT ["java","-jar","app.jar"]
