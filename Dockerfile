FROM maven:3.9.5-openjdk-17 as builder
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY --from=builder /app/target/pets-0.0.1-SNAPSHOT.jar pets-app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/pets-app.jar"]

# docker build -t pets-app .
# docker run -p 8080:8080 pets-app