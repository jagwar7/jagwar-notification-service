# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# 1. Copy only pom.xml to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2. Copy the entire source code
COPY . .

# 3. Build the jar, skipping tests and potential compilation of tests
# Use -Dmaven.test.skip=true to avoid compiling tests entirely
RUN mvn clean package -Dmaven.test.skip=true -B

# Stage 2: Run the application
FROM eclipse-temurin:21-jre
WORKDIR /app

# 4. Use a wildcard cautiously or provide the exact name
# This ensures we pick the correct fat jar created by spring-boot-maven-plugin
COPY --from=build /app/target/notification-service-*.jar app.jar

EXPOSE 5700

# 5. Good practice to use exec form for signals
ENTRYPOINT ["java", "-jar", "app.jar"]
