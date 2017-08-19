
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
	Choose the ip address of docker0 network interface
	
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
		
8.  In order to promote remote connections execute the following commands inside your docker container:

	docker exec -it {CONTAINER_NAME} bash
	service mysql start
	mysql
	create user 'root'@'10.0.2.2' identified by '{USER_PASSWORD}';
	grant all privileges on *.* to 'root'@'10.0.2.2' with grant option;
	flush privileges;
	select user, host from mysql.user where user='root';

9. Now you are ready to run any mysql client with connection binded to localhost:3306 with further port forwarding to docker container.
