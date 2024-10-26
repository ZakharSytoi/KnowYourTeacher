FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/mailingService-0.0.1-SNAPSHOT.jar ./mailingService.jar
COPY src/main/resources/knowyourteacher-425110-5a9fa3ed3900.json /app/knowyourteacher-425110-5a9fa3ed3900.json
COPY src/main/resources/tokens /app/tokens
EXPOSE 8082
CMD ["java" , "-jar", "mailingService.jar"]