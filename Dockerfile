FROM eclipse-temurin:18-jre-alpine
USER root
VOLUME /ttt
WORKDIR /ttt
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ls
RUN chmod a+x ./mvnw 
RUN ./mvnw package -DskipTests
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","jj.tech.paolu.Application","--spring.profiles.active=prod"]