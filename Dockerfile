FROM openjdk:8-jdk-alpine
VOLUME /tmp
#RUN mvn clean install -Dmaven.test.skip=trues
COPY target/*.jar app.jar
EXPOSE 9090
EXPOSE 587
ENTRYPOINT ["java","-jar","/app.jar"]
#
##COPY ./target/auth-0.0.1-SNAPSHOT.jar /usr/app/
##
##WORKDIR /usr/app
##
##RUN sh -c 'touch auth-0.0.1-SNAPSHOT.jar'
##
##ENTRYPOINT ["java","-jar","auth-0.0.1-SNAPSHOT.jar"]
#COPY target/auth-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 9091
#ENTRYPOINT ["java","-jar","/app.jar"]

