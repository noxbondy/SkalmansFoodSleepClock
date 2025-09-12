# Use a Java image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first (for caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make mvnw executable
RUN chmod +x mvnw

# Copy the rest of the project
COPY src ./src

# Build the app
RUN ./mvnw clean package -DskipTests

# Expose port and run
EXPOSE 8080
CMD ["java", "-jar", "target/SkalmansFoodSleepClock-0.0.1-SNAPSHOT.jar"]
