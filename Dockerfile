FROM openjdk:8-jre

RUN mkdir /app
COPY baobiao-1.0-SNAPSHOT.jar /app
CMD java -jar /app/baobiao-1.0-SNAPSHOT.jar.jar
EXPOSE 80