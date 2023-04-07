FROM openjdk:17-alpine3.14
WORKDIR /
COPY target/crypto.recommendation.service-0.0.1-SNAPSHOT.jar ./
EXPOSE 8080:8080
CMD ["java", "-jar", "crypto.recommendation.service-0.0.1-SNAPSHOT.jar"]