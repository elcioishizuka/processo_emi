# How to run locally

## Pre requisites
1. Java 21
2. AWS CLI
3. Docker
4. NoSQL Workbench (to check data in DynamoDB)


## Steps
1. From the folder where `docker-compose.yml`file is located, execute ```docker compose up -d```. SQS server port will be `4566`. DynamoDB server port will be `8000`.
2. For SQS, create a queue named `debit-queue` executing this command ```aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name debit-queue```. Confirm that the queue was created executing ```aws --endpoint-url=http://localhost:4566 sqs list-queues```.
3. For DynamoDB,  create `DebitStatusChange` table executing the following command:
   ```
   aws dynamodb create-table \
   --table-name DebitStatusChange \
   --attribute-definitions \
   AttributeName=customerId,AttributeType=S \
   AttributeName=automaticDebitId,AttributeType=S \
   --key-schema \
   AttributeName=customerId,KeyType=HASH \
   AttributeName=automaticDebitId,KeyType=RANGE \
   --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
   --endpoint-url http://localhost:8000
   ```
4. Use `-Dspring.profiles.active=local` attribute to start application in local environment. Defauld server port will be `8081`.
5. Invoke this is the endpoint for the test `http://localhost:8081/auto-debit/v1/customers/{customerId}/debits/{debitId}/cancel`. Where `customerId` and `debitId` are String valued to be replaced.
6. In order to check if message was created in SQS, execute one of following commands:<br>
   - Receive message: ```aws --endpoint-url=http://localhost:4566 sqs receive-message --queue-url http://sqs.sa-east-1.localhost.localstack.cloud:4566/000000000000/debit-queue```
   - Check message quantity: ```aws --endpoint-url=http://localhost:4566 sqs get-queue-attributes --queue-url http://sqs.sa-east-1.localhost.localstack.cloud:4566/000000000000/debit-queue --attribute-names ApproximateNumberOfMessages```
7. Data in DynamoDB can be checked using `NoSQL Workbench` tool