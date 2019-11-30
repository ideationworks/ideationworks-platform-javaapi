#
# build environment
#
FROM mateothegreat/docker-alpine-gradle-jdk11 as builder

WORKDIR /build

COPY . .

RUN gradle bootJar

RUN ls -lah build/libs

#
# runtime environment
#
FROM openjdk:8-jre-alpine

COPY --from=builder /build/build/libs/build-0.0.3.jar /application.jar

CMD [ "java", "-jar", "/application.jar" ]
