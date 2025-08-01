# Stage 1: Build
FROM eclipse-temurin:17-jdk-jammy as builder

WORKDIR /app

# Кэшируем зависимости
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline

# Копируем исходный код и собираем
COPY src src
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Копируем только JAR
COPY --from=builder /app/target/user-service-*.jar app.jar

# Уменьшаем время запуска за счет проверки зависимостей
RUN java -Djarmode=layertools -jar app.jar extract

# Оптимизированный запуск с layered JAR
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]

EXPOSE 8080