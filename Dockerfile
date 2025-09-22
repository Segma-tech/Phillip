FROM openjdk:22
COPY ./target/DevopsWeek1More-0.1.0.2-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "DevopsWeek1More-0.1.0.2-jar-with-dependencies.jar"]