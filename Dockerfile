FROM adoptopenjdk/openjdk11
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY target/bsf-service-0.0.1-SNAPSHOT.jar BsfServiceApplication.jar
EXPOSE 7171
CMD ["java", "-jar", "BsfServiceApplication.jar"]
