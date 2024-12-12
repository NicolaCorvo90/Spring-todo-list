TODO-list project

To start locally: 
docker compose up -d

To run tests:
make test

To deploy on Docker:
make docker-build

To deploy on SAM:
make serverless-build
sam deploy --guided

Serverless local start: 
make serverless-local-start