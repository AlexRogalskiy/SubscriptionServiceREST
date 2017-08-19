1. Build docker container:

	docker build [--build-arg H2_DB_SOURCE={URL_LINK}] -t={IMAGE_NAME}} .

2. Run docker container:

	docker run -v {LOCAL_FOLDER}:/opt/h2-data -d -p 1521:1521 -p 81:81 --name={CONTAINER_NAME} {IMAGE_NAME}

3. Get logging information on container:

	docker logs -f {CONTAINER_NAME} 2>&1