FROM gradle:8.5-jdk17 AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./

# Download the dependencies
RUN gradle dependencies --no-daemon

COPY src ./src

# Build the application
RUN gradle build --no-daemon

FROM openjdk:17-jdk AS runner

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar ./app.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]