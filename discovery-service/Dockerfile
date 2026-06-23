# ─── Stage 1: Build ───────────────────────────────────────────────
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Make the service's Maven wrapper available
COPY discovery-service/.mvn/ .mvn/
COPY discovery-service/mvnw ./mvnw
RUN chmod +x mvnw

# Install the root parent POM into the local Maven repo so the
# child module can resolve it without hitting Maven Central
COPY pom.xml ./parent-pom.xml
RUN ./mvnw -f parent-pom.xml install -N -q

# Resolve dependencies for the service module
COPY discovery-service/pom.xml ./pom.xml
RUN ./mvnw dependency:go-offline -q

# Build
COPY discovery-service/src ./src
RUN ./mvnw package -Dmaven.test.skip=true -q

# ─── Stage 2: Runtime ─────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
