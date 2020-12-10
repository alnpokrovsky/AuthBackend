FROM gradle as builder
COPY --chown=gradle:gradle . /home/project
WORKDIR /home/project
RUN gradle clean build --rerun-tasks --no-build-cache


FROM openjdk:10-jre-slim
EXPOSE 8080
EXPOSE 8081
RUN mkdir /app
COPY --from=builder /home/project/build/libs/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]
