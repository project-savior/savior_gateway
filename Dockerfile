FROM openjdk:8u302-jre-slim

WORKDIR /usr/local/savior-gateway
ADD ["gateway.jar", "."]
EXPOSE 81

ENTRYPOINT ["java", "-jar", "./gateway.jar"]
