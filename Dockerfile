FROM maven:3.9.0-eclipse-temurin-19-alpine as build
WORKDIR /workspace
COPY pom.xml .
COPY src src
RUN --mount=type=cache,target=/root/.m2,id=m2,sharing=locked \
mvn package -Dmaven.test.skip
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:18-jre-alpine
ARG DEPENDENCY=/workspace/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","jj.tech.paolu.Application","--spring.profiles.active=prod"]