FROM maven:3.9.0-eclipse-temurin-19-alpine as build
WORKDIR /workspace
COPY pom.xml .
COPY src src
RUN mvn -B -f ./pom.xml -s /usr/share/maven/ref/settings-docker.xml package -DskipTests

FROM eclipse-temurin:18-jre-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","jj.tech.paolu.Application","--spring.profiles.active=prod"]