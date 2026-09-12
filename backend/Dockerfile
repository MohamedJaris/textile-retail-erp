# Stage 1: Build with Maven
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY erp-common/pom.xml erp-common/
COPY erp-modules/pom.xml erp-modules/
COPY erp-modules/module-auth/pom.xml erp-modules/module-auth/
COPY erp-app/pom.xml erp-app/
RUN mvn dependency:go-offline -B

COPY erp-common/src erp-common/src
COPY erp-modules/module-auth/src erp-modules/module-auth/src
COPY erp-app/src erp-app/src
RUN mvn package -DskipTests -B

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S erp && adduser -S erp -G erp
COPY --from=build /app/erp-app/target/*.jar app.jar
RUN chown erp:erp app.jar
USER erp
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
