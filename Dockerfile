FROM openjdk:11-jdk as builder

WORKDIR /project
RUN apt update && apt install -y maven

COPY . .
ARG USER_HOME_DIR="/root"

RUN ./mvnw package -Dmaven.test.skip=true

FROM openjdk:11-jdk

WORKDIR /project

COPY --from=builder /project/target/*.jar /project/project.jar

EXPOSE 8080

CMD java -jar /project/project.jar
