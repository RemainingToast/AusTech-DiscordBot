FROM openjdk:17-oracle
WORKDIR /app

COPY ["build/libs/tech-discord-1.0-all.jar", "tech-discord.jar"]
CMD ["java", "-jar", "tech-discord.jar", "--args", "$BOT_TOKEN"]