FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
ENV MAVEN_OPTS="-Xmx150m"
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx150m", "-jar", "app.jar"]