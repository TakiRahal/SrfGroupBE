FROM openjdk:11-jdk-slim
VOLUME /tmp
ADD target/srfgroup-0.0.1-SNAPSHOT.jar springboot-docker-compose.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","springboot-docker-compose.jar"]