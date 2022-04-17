FROM amazoncorretto:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} arr.jar
EXPOSE 8080
EXPOSE 5050
ENTRYPOINT ["java", "-jar", "/app.jar"]
