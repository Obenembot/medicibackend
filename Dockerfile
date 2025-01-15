FROM gradle:7.6.0-jdk17 AS build
WORKDIR /app
#Copy file and dir from local to Docker image
COPY --chown=gradle:gradle . /app
RUN gradle clean build

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]