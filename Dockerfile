FROM eclipse-temurin:18-jre-alpine as builder
WORKDIR /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:18-jre-alpine
WORKDIR /tmp
COPY --from=builder tmp/dependencies/ ./
COPY --from=builder tmp/spring-boot-loader/ ./
COPY --from=builder tmp/snapshot-dependencies/ ./
COPY --from=builder tmp/application/ ./
ENTRYPOINT ["java","-cp","app:app/lib/*","jj.tech.paolu.Application","--spring.profiles.active=prod"]