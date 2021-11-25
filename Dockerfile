FROM tomcat:9.0.55-jdk8

COPY ./target/funcionarios.war /usr/local/tomcat/webapps

EXPOSE 8080

CMD ["catalina.sh", "run"]