# Use OpenJDK 8 JRE as the base image for running the application
FROM openjdk:8-jre-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the pre-built JAR file
COPY target/zookeeper-server-1.0-SNAPSHOT.jar app.jar

# Expose port range for the Spring Boot application
# Since the application uses server.port=0 (random port), we expose a range
EXPOSE 8080-8090

# Set environment variables for ZooKeeper connection with defaults
ENV ZOOKEEPER_CONNECT_STRING=localhost:2181
ENV ZOOKEEPER_TIMEOUT=2000
ENV SERVER_REGISTER_PATH=/servers/test-me

# Run the application
CMD ["java", "-jar", "app.jar"]