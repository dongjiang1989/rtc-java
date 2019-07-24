FROM openjdk:8

RUN mkdir /rtc
WORKDIR /rtc

COPY src ./src
COPY pom.xml .

RUN apt-get update && apt-get install maven -y
RUN mvn clean install -Dmaven.test.skip=true
