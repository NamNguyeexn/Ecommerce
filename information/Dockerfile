FROM openjdk:17-jdk

WORKDIR /app

COPY target/information-0.0.1-SNAPSHOT.jar information.jar

EXPOSE 6066

ENTRYPOINT ["java", "-jar", "information.jar"]