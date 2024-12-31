FROM openjdk:21

WORKDIR /app

COPY ./target/APPMIC-E-CommercePlugins-gateway-2.10.1-SNAPSHOT.jar .

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "APPMIC-E-CommercePlugins-gateway-2.10.1-SNAPSHOT.jar"]