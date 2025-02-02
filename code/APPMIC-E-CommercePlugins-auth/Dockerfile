FROM openjdk:21

WORKDIR /app

COPY ./target/APPMIC-E-CommercePlugins-auth-2.10.1-SNAPSHOT.jar .

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "APPMIC-E-CommercePlugins-auth-2.10.1-SNAPSHOT.jar"]