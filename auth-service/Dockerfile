FROM openjdk:17
LABEL org.opencontainers.image.authors="com.geekbrains"
ADD target/*.jar auth-service.jar
EXPOSE 9098
ENTRYPOINT ["java", "-jar", "auth-service.jar"]
