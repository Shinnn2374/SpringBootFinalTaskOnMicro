FROM eclipse-temurin:17-jdk-alpine
RUN apk add --no-cache maven
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/*.jar"]