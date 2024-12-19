FROM openjdk:17-jdk

WORKDIR /app

COPY target/identify-0.0.1-SNAPSHOT.jar identify.jar

EXPOSE 7071

ENTRYPOINT ["java", "-jar", "identify.jar"]