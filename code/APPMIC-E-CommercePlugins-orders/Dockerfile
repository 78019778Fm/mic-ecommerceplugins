FROM openjdk:21

WORKDIR /app

COPY ./target/APPMIC-E-CommercePlugins-orders-2.10.1-SNAPSHOT.jar .

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "APPMIC-E-CommercePlugins-orders-2.10.1-SNAPSHOT.jar"]