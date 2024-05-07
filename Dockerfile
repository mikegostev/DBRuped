# Use a base image with your desired Java version installed
FROM openjdk:21

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/dbruped-0.0.1-SNAPSHOT.jar /app/dbruped.jar

ENV CHATGPT_API_KEY=

# Specify the command to run your application when the container starts
CMD ["java", "-jar", "/app/dbruped.jar"]