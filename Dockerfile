FROM eclipse-temurin:21
# Set the working directory in the container
WORKDIR /app
# Copy the packaged JAR file into the container at the specified working directory
COPY target/ticket-app-0.0.1-SNAPSHOT.jar /app/tickets-app.jar
# Expose the port that the Spring Boot application will run on
EXPOSE 8080 
# Specify the command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "tickets-app.jar"]
