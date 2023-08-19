FROM openjdk:22-jdk-slim AS runner
WORKDIR /webdiet-frontend
COPY ./target/frontend-*.jar ./webdiet-frontend.jar
COPY ./src/main/resources/application.yml ./application.yml
CMD ["java", "-jar", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5001", "./webdiet-frontend.jar"]