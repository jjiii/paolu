FROM eclipse-temurin:18-jre-alpine as build
USER root
WORKDIR /compile
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN chmod a+x ./mvnw 
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:18-jre-alpine
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","jj.tech.paolu.Application","--spring.profiles.active=prod"]