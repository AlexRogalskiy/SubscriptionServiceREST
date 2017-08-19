
	REST API Newsletter Subscription Service

----------------------------INSTALL-----------------------------

1. 	SET JAVA_HOME to JDK installation folder

2. 	In case of Windows 10 installation execute the following commands:

	set DOCKER_HOST=tcp://192.168.99.101:2376
	set DOCKER_MACHINE_NAME=default2
	set DOCKER_TLS_VERIFY=1
	set DOCKER_TOOLBOX_INSTALL_PATH=C:\Program Files\Docker Toolbox
	set DOCKER_CERT_PATH=C:\Users\{USERNAME}\.docker\machine\machines\default2

2. 	mvn clean package docker:build

3. 	java -jar target/rest-api-newsletter-subscription.war –spring.profiles.active=local
		or
	java -jar target/rest-api-newsletter-subscription.war –spring.profiles.active=prod
		where
			prod - h2 database
			local (by default) - mysql database

--------------------------------------------------------------------
	SETTING UP MySQL via another docker container
--------------------------------------------------------------------
1. docker-machine ls
	Choose your docker-machine name to establish connection with.
	
	If list is empty - set up a new one:
	
		docker-machine create -d virtualbox {DOCKER_MACHINE_NAME}
		docker-machine start {DOCKER_MACHINE_NAME}
		docker-machine env {DOCKER_MACHINE_NAME}
		eval "$(docker-machine env {DOCKER_MACHINE_NAME})"
	
	In case of logging to DOCKER_HUB:
	
		docker login -u='{USER_NAME}' -p='{PASSWORD}'
		
2. docker-machine ssh {DOCKER_MACHINE_NAME}

3. ifconfig
	Choose the ip address of docker0 network
	
4. VirtualBox on settings -> network -> NAT -> port forwarding:

	Add route 172.17.0.1 for mysql
		Name 		-> mysql
		Protocol 	-> TCP
		Host IP 	-> 127.0.0.1
		Host Port 	-> 3306
		Guest IP 	-> 172.17.0.1
		Guest Port	-> 3306
		
5. Cd to folder docker-mysql
6. docker build --tag={IMAGE_NAME} .
7. Run your brand-new container:

	docker run -it -v <local-enabled-dir>:/var/lib/mysql -v <local-log-dir>:/var/log/mysql -p 3306:3306 -e MYSQL_ROOT_PWD={ROOT_PASSWORD} --name {CONTAINER_NAME} {IMAGE_NAME}
		or with a link to another container
	docker run -it -v <local-enabled-dir>:/var/lib/mysql -v <local-log-dir>:/var/log/mysql --link {CONTAINER_NAME_TO_LINK} -p 3306:3306 -e MYSQL_ROOT_PWD={ROOT_PASSWORD} --name {CONTAINER_NAME} {IMAGE_NAME}
		or in a daemon mode
	docker run -d -v <local-enabled-dir>:/var/lib/mysql -v <local-log-dir>:/var/log/mysql -p 3306:3306 -e MYSQL_ROOT_PWD={ROOT_PASSWORD} --name {CONTAINER_NAME} {IMAGE_NAME}
	
	if no image name is being provided then
	
		docker images
		
	Choose the correct one:
	
		docker run -it -p 3306:3306 --name {CONTAINER_NAME} -e MYSQL_ROOT_PASSWORD={ROOT_PASSWORD} {IMAGE_ID}

8. Now you are ready to run any mysql client with connection binded to localhost:3306 with further port forwarding to docker container.

--------------------------------------------------------------------

	To build your docker container manually (with no maven-docker-plugin installation):

		docker build -f {PATH_TO_DOCKER_FILE} -t {APPLICATION_NAME}:{APPLICATION_TAG} .
		docker run --rm -p 8080:8080 {APPLICATION_NAME}:{APPLICATION_TAG}
	
----------------------------GENERAL-----------------------------

URL: http://host:8080/newsletterSub/api/
METHODS: GET / POST / PUT / DELETE
Content-Type: application/json, application/xml

---------------------------BASIC AUTH---------------------------

Basic authentication (ROLES: USER, ADMIN, DBA):

URL: /api/*
ROLE: USER
username: user
password: user123

For basic authentication:
	curl --user user:user123 ...
 
------------------------------JSON------------------------------

1. Get all subscriptions:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/subscriptions

2. Get subscription by id = 1:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/subscription/1

3. Get subscriptions by user id = 1:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/user/1/subscription

4. Create subscription:

curl -d '{"name":"subscription11", "expireAt": "2018-11-11", "type": "PREMIUM"}' -H "Accept: Application/json" -H "Content-Type: application/json" -X POST http://localhost:8080/newsletterSub/api/subscription

5. Update subscription width id = 4:

curl -d '{"name":"subscription4", "expireAt": "2018-11-11", "type": "PREMIUM"}' -H "Accept: application/json" -H "Content-Type: application/json" -X PUT http://localhost:8080/newsletterSub/api/subscription/4

6. Delete subscription with id = 4:

ROLE: ADMIN and DBA
curl -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/subscription/4

7. Delete all subscriptions:

ROLE: ADMIN and DBA
curl -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/subscriptions

8. Get user by id = 1:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/user/1

9. Create user:

curl -d '{"login":"user4@gmail.com", "age": "25", "rating": "1.00", "status": "UNVERIFIED"}' -H "Accept: Application/json" -H "Content-Type: application/json" -X POST http://localhost:8080/newsletterSub/api/user

10. Update user with id = 4:

curl -d '{"login":"user4@gmail.com", "age": "25", "rating": "10.00", "status": "UNVERIFIED"}' -H "Accept: application/json" -H "Content-Type: application/json" -X PUT http://localhost:8080/newsletterSub/api/user/4

11. Delete user with id = 4:

ROLE: ADMIN and DBA
curl -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/user/4

12. Delete all users:

ROLE: ADMIN and DBA
curl -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/users

13. Get all users:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/users

14. Get all users subscribed before date:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/users?date=2017-08-08&order=1

15. Get all users subscribed after date:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/users?date=2017-08-08{&order=0}

16. Get all users by subscription type:

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/users?type=ADVANCED

17. Get all users by subscription type and date (before):

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/users?type=STANDARD&date=2017-08-08&order=1

18. Get list of subscriptions assigned to user (id = 2):

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/user/2/subscriptions

19. Get subscription order with subscription (id = 1) and to user (id = 2):

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/user/2/subscription/1

20. Create subscription order with subscription (id = 1) and user (id = 2):

curl -d '{"subscribedAt": "2017-08-05","expiredAt": "2018-05-05","subscription":{"id": 1,"name": "subscription1","expireAt": 1544562000000,"type": "PREMIUM"}}' -H "Accept: Application/json" -H "Content-Type: application/json" -X POST http://localhost:8080/newsletterSub/api/user/2/subscription

21. Update subscription order with subscription (id = 1) and user (id = 2):

curl -d '{"subscribedAt": "2017-08-05","expiredAt": "2018-06-06","subscription":{"id": 1,"name": "subscription1","expireAt": 1544562000000,"type": "PREMIUM"}}' -H "Accept: Application/json" -H "Content-Type: application/json" -X PUT http://localhost:8080/newsletterSub/api/user/2/subscription/1

22. Delete subscription order with subscription (id = 1) and user (id = 2):

curl -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/user/2/subscription/1

23. Delete all subscription orders with user (id = 2):

curl -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/newsletterSub/api/user/2/subscriptions

24. Get list of users assigned to subscription (id = 2):

curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/newsletterSub/api/subscription/2/users

-------------------------------XML------------------------------

1. Get all subscriptions:

curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/subscriptions

2. Get subscription by id = 1:

curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/subscription/1

3. Get subscriptions by user id = 1:

curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/user/1/subscription

4. Create subscription:

curl -d '<Subscription><name>subscription11</name><expireAt>2018-11-11</expireAt><type>PREMIUM</type></Subscription>' -H "Accept: application/xml" -H "Content-Type: application/xml" -X POST http://localhost:8080/newsletterSub/api/subscription

5. Update subscription width id = 4:

curl -d '<Subscription><name>subscription4</name><expireAt>2018-11-11</expireAt><type>PREMIUM</type></Subscription>' -H "Accept: application/xml" -H "Content-Type: application/xml" -X PUT http://localhost:8080/newsletterSub/api/subscription/4

6. Delete subscription with id = 4:

ROLE: ADMIN and DBA
curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X DELETE http://localhost:8080/newsletterSub/api/subscription/4

7. Delete all subscriptions:

ROLE: ADMIN and DBA
curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X DELETE http://localhost:8080/newsletterSub/api/subscriptions

8. Get user by id = 1:

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/user/1

9. Create user:

curl -d '<User><login>user5@gmail.com</login><age>25</age><rating>1.00</rating><status>UNVERIFIED</status></User>' -H "Accept: application/xml" -H "Content-Type: application/xml" -X POST http://localhost:8080/newsletterSub/api/user

10. Update user with id = 4:

curl -d '<User><login>user5@gmail.com</login><age>25</age><rating>10.00</rating><status>UNVERIFIED</status></User>' -H "Accept: application/xml" -H "Content-Type: application/xml" -X PUT http://localhost:8080/newsletterSub/api/user/4

11. Delete user with id = 4:

ROLE: ADMIN and DBA
curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X DELETE http://localhost:8080/newsletterSub/api/user/4

12. Delete all users:

ROLE: ADMIN and DBA
curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X DELETE http://localhost:8080/newsletterSub/api/users

13. Get all users:

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/users

14. Get all users subscribed before date:

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/users?date=2017-08-08&order=1

15. Get all users subscribed after date:

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/users?date=2017-08-08{&order=0}

16. Get all users by subscription type:

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/users?type=ADVANCED

17. Get all users by subscription type and date (before):

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/users?type=STANDARD&date=2017-08-08&order=1

18. Get list of subscriptions assigned to user (id = 2):

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/user/2/subscriptions

19. Get subscription order with subscription (id = 1) and to user (id = 2):

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/user/2/subscription/1

20. Create subscription order with subscription (id = 1) and user (id = 2):

curl -d '<UserSubOrder><subscribedAt>2017-08-05</subscribedAt><expiredAt>2018-05-05</expiredAt><subscription><id>1</id><name>subscription1</name><expireAt>1544562000000</expireAt><type>PREMIUM</type></subscription></UserSubOrder>' -H "Accept: application/xml" -H "Content-Type: application/xml" -X POST http://localhost:8080/newsletterSub/api/user/2/subscription

21. Update subscription order with subscription (id = 1) and user (id = 2):

curl -d '<UserSubOrder><subscribedAt>2017-08-05</subscribedAt><expiredAt>2018-06-06</expiredAt><subscription><id>1</id><name>subscription1</name><expireAt>1544562000000</expireAt><type>PREMIUM</type></subscription></UserSubOrder>' -H "Accept: application/xml" -H "Content-Type: application/xml" -X PUT http://localhost:8080/newsletterSub/api/user/2/subscription/1

22. Delete subscription order with subscription (id = 1) and user (id = 2):

curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X DELETE http://localhost:8080/newsletterSub/api/user/2/subscription/1

23. Delete all subscription orders with user (id = 2):

curl -H "Accept: application/xml" -H "Content-Type: application/xml" -X DELETE http://localhost:8080/newsletterSub/api/user/2/subscriptions

24. Get list of users assigned to subscription (id = 2):

curl -i -H "Accept: application/xml" -H "Content-Type: application/xml" -X GET http://localhost:8080/newsletterSub/api/subscription/2/users
