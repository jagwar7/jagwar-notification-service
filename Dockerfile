#1 : INITIAL SETUP , COPY FILES FROM PROJECT DIRECTORY ---> DOCKER CONTAINER DIRECTORY
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

#2 : EXTRACT LAYERS
FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar


#3 : RUN AS NON-ROOT FOR SECURITY
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

#4 : RUN ON PORT
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]