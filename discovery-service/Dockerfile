FROM openjdk:17
LABEL org.opencontainers.image.authors="com.geekbrains"
ADD target/*.jar discovery-service.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "discovery-service.jar"]
