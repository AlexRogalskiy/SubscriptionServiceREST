# Dockerfile
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/rest-api-newsletter-subscription.war app.war
ENV JAVA_OPTS="-Dspring.profiles.active=prod"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.war" ]
