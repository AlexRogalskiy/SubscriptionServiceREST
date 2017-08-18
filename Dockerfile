# Dockerfile
FROM openjdk:8-jdk-alpine
EXPOSE 8080
VOLUME /tmp
ADD /target/rest-api-newsletter-subscription.war rest-api-newsletter-subscription.war
ENV JAVA_OPTS="-Dspring.profiles.active=local"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar rest-api-newsletter-subscription.war" ]