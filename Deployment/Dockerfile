FROM openjdk:8-jre-alpine

ENV DATABASE_HOST=mysql
ENV DATABASE_PORT=3306
ENV DATABASE_NAME=portal
ENV DATABASE_USER=root
ENV DATABASE_PASSWORD=123

ARG JAR_FILE
COPY ${JAR_FILE} app.jar

EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=container","-jar", "/app.jar"]
