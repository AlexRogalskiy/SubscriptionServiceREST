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