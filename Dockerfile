# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-slim

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Install dependencies and compile
# Note: Assuming gson is in lib/
RUN javac -cp "lib/*;src" -d bin src/Main.java src/web/WebServer.java

# Expose port 8080 for the web server
EXPOSE 8080

# Run the application
# Use -cp to include bin and lib
CMD ["java", "-cp", "bin:lib/*", "Main"]
