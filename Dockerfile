FROM openjdk:8-jdk-alpine
LABEL maintainer="mathias@beanleaf.be"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=vim-web/target/vim-web.jar
ADD ${JAR_FILE} vim-web.jar
ENTRYPOINT ["java","${EXTRA_JAVA_OPTS}","-jar","/vim-web.jar"]