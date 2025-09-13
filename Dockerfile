# Use a Java 17 slim image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper & pom.xml first (for caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make mvnw executable
RUN chmod +x mvnw

# Copy source code
COPY src ./src

# Build the project
RUN ./mvnw clean package -DskipTests

# Expose default port (optional)
EXPOSE 8080

# Run the application with Render's dynamic PORT
CMD ["sh", "-c", "java -Dserver.port=$PORT -jar target/SkalmansFoodSleepClock-0.0.1-SNAPSHOT.jar"]