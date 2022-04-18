# Two day ATM assignment
Two day assigmnet of an atm rest service.

## Requirements

- [JDK 1.8](https://docs.aws.amazon.com/corretto/latest/corretto-8-ug/downloads-list.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

This project can be via the spring boot mvn plugin. Run the following command from the root of your checkout directory

```
mvn spring-boot:run
```
The services will be available on http://localhost:8080</br>

## Running in a container
 
Alternatively this project can be run in a container [Docker Desktop](https://maven.apache.org). This can be achieved by running the following from the root directory of your checkout:

```
mvn clean package
docker build -t atm-assignment:1 .
docker run --rm --name atm-assignment -p 8080:8080 atm-assignment:1
```
The services will be available on http://localhost:8080 </br>
Upon closing the terminal the container/service will be destroyed.

## Using the service
Two GET mappings are presented by this service.

The first is to query the balance of a account:
```
/api/v1/account/[Account ID]/[Pin Number]/balance

example: http://localhost:8080/api/v1/account/123456789/1234/balance
```
The second mapping is to withdraw funds: 
```
/api/v1/account/[Account ID]/[pin number]/withdraw/[ammount]

example: http://localhost:8080/api/v1/account/123456789/1234/withdraw/995
```
## Test Coverage Report
After the execution of `mvn test`, a html coverage report will generate. </br>This can be viewed by opening `target/site/jacoco/index.html`

