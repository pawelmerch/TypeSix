FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /app
COPY . /app/.
RUN mvn -f /app/pom.xml clean package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/TypeSixStarter/target/*.jar /app/*.jar

# POSTGRES IN YC
RUN mkdir -p /root/.postgresql
RUN wget "https://storage.yandexcloud.net/cloud-certs/CA.pem" --output-document /root/.postgresql/root.crt
RUN chmod 777 /root/.postgresql/root.crt

# KAFKA IN YC
RUN apk update && apk install kafkacat

ENV MIN_MEMORY=512m
ENV MAX_MEMORY=1024m

ENTRYPOINT java -jar -Xms${MIN_MEMORY} -Xmx${MAX_MEMORY} /app/*.jar
