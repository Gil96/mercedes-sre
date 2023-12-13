FROM openjdk:17-jdk
WORKDIR /tmp
COPY target/sre-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "sre-0.0.1-SNAPSHOT.jar"]