# Use lightweight JDK image
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy project files
COPY . .

# Build project
RUN ./mvnw clean package -DskipTests

# Run jar
CMD ["java", "-jar", "target/gpl-0.0.1-SNAPSHOT.jar"]