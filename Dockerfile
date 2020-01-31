FROM openjdk:11
LABEL maintainer="mathias@beanleaf.be"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=vim-web/target/vim-web.jar
ADD ${JAR_FILE} vim-web.jar
ENTRYPOINT ["java","-jar","/vim-web.jar"]