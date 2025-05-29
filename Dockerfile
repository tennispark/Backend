FROM openjdk:21-jdk-slim

COPY /build/libs/tennis-park-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]