# Use a lightweight base image with JRE installed
FROM openjdk:17-jdk-alpine AS runtime

# Set the working directory
WORKDIR /app

# Copy the application source code
COPY . .

# Build the application
RUN ./mvnw package -DskipTests

# Set the entrypoint to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "./target/ticketify-0.0.1-SNAPSHOT.jar"]

# Expose the port that the application listens on
EXPOSE 8080
