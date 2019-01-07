FROM openjdk:8-jdk-alpine
LABEL Highlander Dantas
VOLUME /tmp
ENV PORT=8880
ENV PROFILE=prod,oauth-security
ENV ORIGIN_LOCAL=http://localhost:${PORT}
ENV ACESS_KEY=${ACESS_KEY}
ENV SECRET_KEY=${SECRET_KEY}
ENV USERNAME=${USERNAME}
ENV SENHA_APP=${SENHA_APP}
ENV EMAIL_PAGSEGURO=${EMAIL_PAGSEGURO}
ENV TOKEN_PAGSEGURO=${TOKEN_PAGSEGURO}
ENV URL_BANCO=${URL_BANCO}
ENV USER_BANCO=${USER_BANCO}
ENV PASSWORD_BANCO=${PASSWORD_BANCO}
ADD target/teatrou.jar teatrou.jar
EXPOSE $PORT
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=${PROFILE}",  "-jar",  "teatrou.jar"]

