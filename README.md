# Spring Boot, PostgreSQL, and Kafka demo

This workspace now contains a minimal Spring Boot application that:

- creates users, products, and orders in PostgreSQL
- seeds initial data on startup
- exposes a simple order API at `/orders`
- publishes order events to the Kafka topic `orders-events`
- consumes the same events with a Kafka listener

## Run the stack

From the repository root:

```bash
docker compose -f .devcontainer/docker-compose.yml up -d
```

## Build and run the app

Inside the dev container, install Maven if needed:

```bash
apt-get update && apt-get install -y maven
```

Then:

```bash
mvn spring-boot:run
```

## Sample requests

Create an order:

```bash
curl -X POST "http://localhost:8080/orders?userId=1&productId=1&quantity=2"
```

List orders:

```bash
curl http://localhost:8080/orders
```

## Useful database and Kafka commands

```bash
docker exec -it devcontainer-postgres-1 psql -U postgres -d springboot
```

```bash
docker exec -it devcontainer-kafka-1 bash
/opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
```

# mvn spring-boot:run
# lsof -i :8080
# kill 20420

# Start the supporting services:
docker compose -f docker-compose.yml up -d

sudo apt update
sudo apt install maven -y

# Start the app:
mvn spring-boot:run
# Create an order:
curl -X POST "http://localhost:8081/orders?userId=1&productId=1&quantity=2"
# List orders:
curl http://localhost:8081/orders

# enter kafka container
docker exec -it devcontainer-kafka-1 bash

fc8b5ebc6227:/$ find / -name kafka-topics.sh 2>/dev/null
/opt/kafka/bin/kafka-topics.sh

/opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

# enter postgres container
docker exec -it devcontainer-postgres-1 psql -U postgres
SELECT version();
\l
\c springboot
\dt
\q


lsof -i :8081

# run mvn spring boot inside container
docker compose -f .devcontainer/docker-compose.yml run --rm --service-ports app mvn spring-boot:run

# with mvn installed
docker compose -f .devcontainer/docker-compose.yml run --rm --service-ports app bash -lc "apt-get update && apt-get install -y maven && mvn spring-boot:run"

docker compose -f .devcontainer/docker-compose.yml run --rm --service-ports -w /workspace app bash -lc "apt-get update && apt-get install -y maven openjdk-21-jdk && mvn -f pom.xml org.springframework.boot:spring-boot-maven-plugin:3.3.3:run"