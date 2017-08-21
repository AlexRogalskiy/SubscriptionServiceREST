# REST API Newsletter Subscription Service

[![N|WildBeesLabs](https://cldup.com/dTxpPi9lDf.thumb.png)](https://projects.spring.io/)

Custom REST API newsletter subscription service.

### General
See application.yml
Default URL: http://host:8080/newsletterSub/api/
METHODS: GET / POST / PUT / DELETE
Content-Type: application/json, application/xml

Basic Auth:
Basic authentication roles (USER, ADMIN, DBA):
URL: /api/*
ROLE: USER
username: user
password: user123

```sh
curl --user user:user123 ...
```

### REST API
 - JSON: -H "Accept: application/json" -H "Content-Type: application/json"
 - XML: -H "Accept: application/xml" -H "Content-Type: application/xml"

1. Get all subscriptions:
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/subscriptions
```
2. Get subscription by id = 1:
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/subscription/1
```
3. Get subscriptions by user id = 1:
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/user/1/subscription
```
4. Create subscription:
```sh
curl -d '{"name":"subscription11", "expireAt": "2018-11-11", "type": "PREMIUM"}' -H "Accept: Application/json" -H "Content-Type: application/json" -X POST http://localhost:8080/newsletterSub/api/subscription
```
5. Update subscription width id = 4:
```sh
curl -d '{"name":"subscription4", "expireAt": "2018-11-11", "type": "PREMIUM"}' -H "Accept: application/json" -H "Content-Type: application/json" -X PUT http://localhost:8080/newsletterSub/api/subscription/4
```
6. Delete subscription with id = 4:
```sh
ROLE: ADMIN and DBA
curl --user admin:adminr123 -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/subscription/4
```
7. Delete all subscriptions:
```sh
ROLE: ADMIN and DBA
curl --user admin:adminr123 -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/subscriptions
```
8. Get user by id = 1:
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/user/1
```
9. Create user:
```sh
curl -d '{"login":"user4@gmail.com", "age": "25", "rating": "1.00", "status": "UNVERIFIED"}' -H "Accept: Application/json" -H "Content-Type: application/json" -X POST http://localhost:8080/newsletterSub/api/user
```
10. Update user with id = 4:
```sh
curl -d '{"login":"user4@gmail.com", "age": "25", "rating": "10.00", "status": "UNVERIFIED"}' -H "Accept: application/json" -H "Content-Type: application/json" -X PUT http://localhost:8080/newsletterSub/api/user/4
```
11. Delete user with id = 4:
```sh
ROLE: ADMIN and DBA
curl --user admin:adminr123 -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/user/4
```
12. Delete all users:
```sh
ROLE: ADMIN and DBA
curl --user admin:adminr123 -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/users
```
13. Get all users:
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/users
```
14. Get all users subscribed before date:
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/users?date=2017-08-08&order=1
```
15. Get all users subscribed after date:
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/users?date=2017-08-08{&order=0}
```
16. Get all users by subscription type:
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/users?type=ADVANCED
```
17. Get all users by subscription type and date (before):
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/users?type=STANDARD&date=2017-08-08&order=1
```
18. Get list of subscriptions assigned to user (id = 2):
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/user/2/subscriptions
```
19. Get subscription order with subscription (id = 1) and to user (id = 2):
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/user/2/subscription/1
```
20. Create subscription order with subscription (id = 1) and user (id = 2):
```sh
curl -d '{"subscribedAt": "2017-08-05","expiredAt": "2018-05-05","subscription":{"id": 1,"name": "subscription1","expireAt": 1544562000000,"type": "PREMIUM"}}' -H "Accept: Application/json" -H "Content-Type: application/json" -X POST http://localhost:8080/newsletterSub/api/user/2/subscription
```
21. Update subscription order with subscription (id = 1) and user (id = 2):
```sh
curl -d '{"subscribedAt": "2017-08-05","expiredAt": "2018-06-06","subscription":{"id": 1,"name": "subscription1","expireAt": 1544562000000,"type": "PREMIUM"}}' -H "Accept: Application/json" -H "Content-Type: application/json" -X PUT http://localhost:8080/newsletterSub/api/user/2/subscription/1
```
22. Delete subscription order with subscription (id = 1) and user (id = 2):
```sh
curl -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/user/2/subscription/1
```
23. Delete all subscription orders with user (id = 2):
```sh
curl -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/user/2/subscriptions
```
24. Get list of users assigned to subscription (id = 2):
```sh
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/subscription/2/users
```

### Installation

Service requires [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) to run.

Install the dependencies to start the server.

1. SET JAVA_HOME to JDK/JRE installation folder
2. For Docker container installation (in case of Windows 10) execute the following commands:
```sh
set DOCKER_HOST=tcp://{DOCKER_MACHINE_HOST}:{DOCKER_MACHINE_PORT}
set DOCKER_MACHINE_NAME={DOCKER_MACHINE_NAME}
set DOCKER_TLS_VERIFY=1
set DOCKER_TOOLBOX_INSTALL_PATH=C:\Program Files\Docker Toolbox
set DOCKER_CERT_PATH=C:\Users\{USERNAME}\.docker\machine\machines\{DOCKER_MACHINE_NAME}
```
3. Go to folder:
```sh
cd SubscriptionServiceREST
```
4. Install the dependencies (by default, the Docker will expose port 8080):
```sh
mvn clean package docker:build
```
5. Run the application server:
```sh
java -jar target/rest-api-newsletter-subscription.war –spring.profiles.active=local
```
or
```sh
java -jar target/rest-api-newsletter-subscription.war –spring.profiles.active=prod
```
where
 - prod - h2 database
 - local (by default) - mysql database

6. To build your docker container manually (with no maven-docker-plugin installation):
```sh
docker build -f {PATH_TO_DOCKER_FILE} -t {APPLICATION_NAME}:{APPLICATION_TAG} .
docker run -d --restart="always" --rm -p 8080:8080 {APPLICATION_NAME}:{APPLICATION_TAG}
```

### SETTING UP MySQL via another docker container
Execute the following commands:
1. Choose your docker-machine name to establish connection with
```sh
docker-machine ls
```
If list is empty - set up a new one:
```sh
docker-machine create -d virtualbox {DOCKER_MACHINE_NAME}
docker-machine start {DOCKER_MACHINE_NAME}
docker-machine env {DOCKER_MACHINE_NAME}
eval "$(docker-machine env {DOCKER_MACHINE_NAME})"
```
In case of logging to DOCKER_HUB:
```sh
docker login -u='{USER_NAME}' -p='{PASSWORD}'
```
2. Connect to docker machine via ssh:
```sh
docker-machine ssh {DOCKER_MACHINE_NAME}
```
3.Choose the IP address of <docker0> network
```sh
ifconfig
```
4. Set up your route in VirtualBox network settings (Settings -> Network -> NAT -> Port forwarding):
 - Name 		-> mysql
 - Protocol 	-> TCP
 - Host IP 	-> 127.0.0.1
 - Host Port 	-> 3306
 - Guest IP 	-> 172.17.0.1
 - Guest Port	-> 3306

 | Name | Protocol | Host IP | Host Port | Guest IP | Guest Port |
| ------ | ------ | ------ | ------ | ------ | ------ |
| mysql | TCP | 127.0.0.1 | 3306 | 172.17.0.1 | 3306

5. Go to docker-mysql folder:
```sh
folder docker-mysql
```
6. Build docker image:
```sh
docker build --tag={IMAGE_NAME} .
```
7. Run docker image in a docker container:
```sh
docker run -it -v <local-enabled-dir>:/var/lib/mysql -v <local-log-dir>:/var/log/mysql -p 3306:3306 -e MYSQL_ROOT_PWD={ROOT_PASSWORD} --name {CONTAINER_NAME} {IMAGE_NAME}
```
or with a link to another container
```sh
docker run -it -v <local-enabled-dir>:/var/lib/mysql -v <local-log-dir>:/var/log/mysql --link {CONTAINER_NAME_TO_LINK} -p 3306:3306 -e MYSQL_ROOT_PWD={ROOT_PASSWORD} --name {CONTAINER_NAME} {IMAGE_NAME}
```
or in a daemon mode
```sh
docker run --restart="always" -d -v <local-enabled-dir>:/var/lib/mysql -v <local-log-dir>:/var/log/mysql -p 3306:3306 -e MYSQL_ROOT_PWD={ROOT_PASSWORD} --name {CONTAINER_NAME} {IMAGE_NAME}
```	
if no image name is being provided then
```sh
docker images
docker run --restart="always" -d -p 3306:3306 --name {CONTAINER_NAME} -e MYSQL_ROOT_PWD={ROOT_PASSWORD} {IMAGE_ID}
```	
8. Now you are ready to run any mysql client with connection binded to localhost:3306 with further port forwarding to docker container.

### SETTING UP H2 DB via another docker container
1. Go to folder docker-h2:
```sh
cd docker-h2
```	
2. Build docker container:
```sh
docker build [--build-arg H2_DB_SOURCE={URL_LINK}] -t={IMAGE_NAME}} .
```	
3. Run docker container:
```sh
docker run -v {LOCAL_FOLDER}:/opt/h2-data --restart="always" -d -p 1521:1521 -p 81:81 --name={CONTAINER_NAME} {IMAGE_NAME}
```	
4. Get logging information about container:
```sh
docker logs -f {CONTAINER_NAME} 2>&1
```	

### Todos

 - Write addintional Tests
 - Add new functionality

License
----

MIT

   [spring-boot]: <https://projects.spring.io/spring-boot/>
   [docker-mysql]: <https://github.com/AlexRogalskiy/SubscriptionServiceREST/blob/master/docker-mysql/README.md>
   [docker-h2]: <https://github.com/AlexRogalskiy/SubscriptionServiceREST/blob/master/docker-h2/README.md>
