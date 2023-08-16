#Build stage
FROM maven:3.6.3-openjdk-17-slim AS builder
WORKDIR /webdiet-frontend
COPY src ./src
COPY pom.xml ./
RUN mvn -f ./pom.xml clean package

#Package stage
FROM openjdk:22-slim-bullseye AS runner
WORKDIR /webdiet-frontend
COPY --from=builder /webdiet-frontend/target/frontend-*.jar ./webdiet-frontend.jar
COPY --from=builder /webdiet-frontend/src/main/resources/application.yml ./application.yml
CMD ["java", "-jar", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5001", "./webdiet-frontend.jar"]