# downloads image from dockerhub
FROM openjdk:11
ADD target/subscription-docker.jar subscription-docker.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "subscription-docker.jar"]