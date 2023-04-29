FROM boardgamespring-base:latest AS builder
WORKDIR /project
COPY ./ /project/
RUN mvn clean package

FROM alpine:latest
RUN apk add --no-cache openjdk17-jre-headless
WORKDIR /project
COPY --from=builder /project/target/boardgameSpring-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]