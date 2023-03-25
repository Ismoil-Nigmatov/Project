FROM java:11-jdk as builder

COPY ./target/project-0.0.1-SNAPSHOT.jar /project/project.jar

WORKDIR /project

RUN sh -c 'touch project-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","project-0.0.1-SNAPSHOT.jar"]
