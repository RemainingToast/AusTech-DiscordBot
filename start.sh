#!/bin/sh

echo Hello "$BOT_TOKEN"
./gradlew run --args "$BOT_TOKEN"
