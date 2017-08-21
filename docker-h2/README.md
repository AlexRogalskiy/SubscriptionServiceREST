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