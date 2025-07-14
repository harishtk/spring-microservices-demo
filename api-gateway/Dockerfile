FROM gradle:8.5-jdk17 AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Download dependencies
RUN gradle dependencies --no-daemon

COPY src ./src

# Build the application
RUN gradle build --no-daemon

FROM openjdk:17-jdk AS runner

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar ./app.jar

EXPOSE 9765

ENTRYPOINT ["java", "-jar", "app.jar"]