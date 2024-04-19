# Define the base image
FROM maven:3.9.6-eclipse-temurin-11-alpine

# Create the application directory (optional, but explicit)
WORKDIR /usr/src/myapp

# Copy all contents from the current context to the workdir
COPY . .

# Set the environment variable for the token
ENV TOKEN=ghp_EprEZK00EHIfCDoX3LTJITHWRhXGCJ2T6I

# Print the token (for verification purposes)
RUN echo $TOKEN

# Run the application using spring-boot:run command
CMD ["mvn", "spring-boot:run"]

# Expose the port for container communication
EXPOSE 8090
