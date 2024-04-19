# Define the base image
FROM maven:3.9.6-eclipse-temurin-11-alpine

# Create the application directory
WORKDIR /usr/src/myapp

# Copy the application files from the current context
COPY . .

# Set the environment variable for the token
ENV TOKEN=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

# Print the token (for verification purposes)
RUN echo $TOKEN

# Install dependencies and build the application
RUN mvn clean install

# Run the application
CMD ["java", "-jar", "target/hello-world-app-1.0-SNAPSHOT.jar"]

# Expose the port for container communication
EXPOSE 8090
