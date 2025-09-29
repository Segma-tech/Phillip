FROM openjdk:22
COPY ./target/alpha-0.1.0.2-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "alpha-0.1.0.2-jar-with-dependencies.jar"]