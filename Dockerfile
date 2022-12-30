FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN sudo ./mvnw dependency:resolve

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]