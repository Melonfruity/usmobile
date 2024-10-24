# Use a base image that includes Java (JDK)
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven build output (JAR file) to the container
COPY target/*.jar app.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expose the port to port 8081
EXPOSE 8081