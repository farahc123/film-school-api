# builder stage
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /workspace
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn -q -B -e -DskipTests dependency:go-offline
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -q -B -DskipTests package

#runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /workspace/target/*.jar /app/app.jar

# exposes the port the app runs on (matches application.properties)
EXPOSE 8091

# allows profile override at runtime
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

ENTRYPOINT ["java", "-jar", "/app/app.jar"]