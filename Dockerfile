FROM adoptopenjdk:8-jdk-hotspot AS builder

ARG TOKEN
ENV BOT_TOKEN=$TOKEN

WORKDIR /discordbot
COPY gradle ./gradle
COPY gradlew build.gradle settings.gradle ./
RUN ./gradlew --no-daemon dependencies
COPY . .
RUN ./gradlew --no-daemon build

CMD ["./gradlew", "run", "--args", "$BOT_TOKEN"]

#FROM adoptopenjdk:8-jre-hotspot

#WORKDIR /discordbot
#COPY --from=builder /discordbot/build/libs/AusTechBot-0.0.1-all.jar ./AusTechBot.jar

#CMD ["java", "-jar", "AusTechBot.jar", "--args", "$TOKEN"]