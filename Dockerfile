FROM openjdk:17-oracle

ARG JAR_FILE=target/epp-dictionary.jar
COPY ${JAR_FILE} app.jar

EXPOSE 7095

ENTRYPOINT ["java","-jar","-XX:+UseSerialGC","-Xss512k","-XX:MaxRAM=256m","/app.jar"]

