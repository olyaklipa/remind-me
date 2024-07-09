FROM openjdk:17-jdk
WORKDIR /app
COPY target/remind-me-0.0.1-SNAPSHOT.jar remind-me.jar
EXPOSE 8080
CMD ["java", "-jar", "remind-me.jar"]