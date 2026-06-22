# ─── Stage 1: Build ───────────────────────────────────────────────
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

COPY sentiment-service/.mvn/ .mvn/
COPY sentiment-service/mvnw ./mvnw
RUN chmod +x mvnw

# Install the root parent POM into the local Maven repo
COPY pom.xml pom.xml
RUN ./mvnw -f pom.xml install -N -q

# Build & install comment-insight-common so this service can resolve it
COPY comment-insight-common/pom.xml comment-insight-common/pom.xml
RUN ./mvnw -f comment-insight-common/pom.xml dependency:go-offline -q
COPY comment-insight-common/src comment-insight-common/src
RUN ./mvnw -f comment-insight-common/pom.xml install -Dmaven.test.skip=true -q

# Resolve dependencies, then build the service
COPY sentiment-service/pom.xml sentiment-service/pom.xml
RUN ./mvnw -f sentiment-service/pom.xml dependency:go-offline -q
COPY sentiment-service/src sentiment-service/src
RUN ./mvnw -f sentiment-service/pom.xml package -Dmaven.test.skip=true -q

# ─── Stage 2: Runtime ─────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=builder /app/sentiment-service/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
