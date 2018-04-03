FROM openjdk:8-jre-alpine
WORKDIR /usr/src/HomologationPortal/
RUN touch app.jar
COPY lab-0.0.1-SNAPSHOT.jar /usr/src/HomologationPortal/app.jar
EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=container","-jar", "app.jar"]