# Use a base image containing Java runtime
FROM eclipse-temurin:21-jdk-alpine

# The application's jar file
ARG JAR_FILE=target/*.jar

# Copy the application's jar to the container
COPY ${JAR_FILE} app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8083

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
