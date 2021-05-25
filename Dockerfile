FROM adoptopenjdk/openjdk11:latest
MAINTAINER baeldung.com

ENV DB_USER=maickeen \
    DB_PASSWORD=petz2021

COPY target/phone-0.0.1-SNAPSHOT.jar phone-1.0.0.jar
ENTRYPOINT ["java","-jar","/phone-1.0.0.jar"]