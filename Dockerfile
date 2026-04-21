# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app


RUN mvn dependency:go-offline -B


COPY . .


RUN mvn clean package -Dmaven.test.skip=true -B -e -X



FROM eclipse-temurin:21-jre
WORKDIR /app



COPY --from=build /app/target/notification-service-*.jar app.jar

EXPOSE 5700


ENTRYPOINT ["java", "-jar", "app.jar"]
