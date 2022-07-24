FROM openjdk:17
LABEL org.opencontainers.image.authors="com.geekbrains"
ADD target/*.jar debit-service.jar
EXPOSE 9099
ENTRYPOINT ["java", "-jar", "debit-service.jar"]
