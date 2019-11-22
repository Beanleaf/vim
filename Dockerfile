FROM openjdk:8-jdk-alpine
ARG EXTRA_JAVA_OPTS
LABEL maintainer="mathias@beanleaf.be"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=vim-web/target/vim-web.jar
COPY ${JAR_FILE} vim-web.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","$EXTRA_JAVA_OPTS","-jar","/vim-web.jar"]