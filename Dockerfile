#FROM openjdk:21-jdk-slim (기존꺼)

FROM eclipse-temurin:21-jdk

COPY build/libs/tennis-park-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]