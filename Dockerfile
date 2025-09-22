FROM openjdk:22
COPY ./target/DevopsWeek1More-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "DevopsWeek1More-1.0-SNAPSHOT-jar-with-dependencies.jar"]