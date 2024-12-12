start:
	docker compose up -d
	./gradlew bootRun

test:
	docker compose up -d
	./gradlew test

docker-build:
	make test
	./gradlew build
	docker compose up --build

serverless-build:
	./gradlew clean build
	./gradlew build
	sam build

serverless-local-start:
	make serverless-build
	sam local start-api
