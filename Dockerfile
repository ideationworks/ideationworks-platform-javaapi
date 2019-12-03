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
FROM mateothegreat/docker-alpine-gradle-jre11

COPY --from=builder /build/build/libs/ideationworks-0.0.1.jar /application.jar

CMD [ "java", "-jar", "/application.jar" ]
