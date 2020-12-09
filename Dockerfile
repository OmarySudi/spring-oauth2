#FROM java:8-jdk-alpine
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


From openjdk:8
copy ./target/auth-0.0.1-SNAPSHOT.jar auth-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","auth-0.0.1-SNAPSHOT.jar"]

