FROM openjdk:17
LABEL org.opencontainers.image.authors="com.geekbrains"
ADD target/*.jar credit-service.jar
EXPOSE 9100
ENTRYPOINT ["java", "-jar", "credit-service.jar"]
