FROM openjdk:22
COPY ./target/classes/imc /tmp/imc
WORKDIR /tmp
ENTRYPOINT ["java", "imc.com.App"]