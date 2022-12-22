FROM adoptopenjdk:8-jdk-hotspot AS builder

WORKDIR /discordbot
COPY gradle ./gradle
COPY gradlew build.gradle settings.gradle ./
RUN ./gradlew --no-daemon dependencies
COPY . .
RUN ./gradlew --no-daemon build

ENV BOT_TOKEN=""

RUN chmod +x ./start.sh

CMD ["./start.sh"]

#FROM adoptopenjdk:8-jre-hotspot

#WORKDIR /discordbot
#COPY --from=builder /discordbot/build/libs/AusTechBot-0.0.1-all.jar ./AusTechBot.jar

#CMD ["java", "-jar", "AusTechBot.jar", "--args", "$TOKEN"]