FROM eclipse-temurin:18-jre-alpine
VOLUME /tmp
RUN ./mvnw install -DskipTests
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","jj.tech.paolu.Application","--spring.profiles.active=prod"]