# 📘 Reliable Orders Application

A sample microservice that demonstrates **reliable order processing** using:

- **Spring Boot 3**
- **PostgreSQL** (order persistence)
- **Apache Kafka** (event-driven communication)
- **Docker Compose** (for local infrastructure)

---

## ⚙️ Features
- REST API for creating and retrieving orders.
- Kafka **producer** publishes `OrderCreatedEvent` when an order is saved.
- Kafka **consumer** processes events and applies business logic.
- Dead Letter Topic (**DLT**) + error handler for failed message processing.
- Modular design: `Controller → Service → Repository → Producer/Consumer`.

---

## 🏗️ Architecture

```
        [ REST Client ]
             |
             v
     ┌───────────────────┐
     │  OrderController  │  <-- REST endpoints (/orders)
     └───────────────────┘
               |
               v
     ┌───────────────────┐
     │   OrderService    │  <-- Business logic + persistence
     └───────────────────┘
               |
               v
     ┌───────────────────┐
     │  OrderRepository  │  <-- PostgreSQL
     └───────────────────┘

Kafka flow:
    OrderController ---> OrderProducer ---> [Kafka Topic: orders.v1]
                                               |
                                               v
                                         OrderConsumer
```

---

## 🚀 Getting Started

### 1. Clone Repository
```bash
git clone https://github.com/fakhriafrasiyab/ReliableOrdersApplication.git
cd ReliableOrdersApplication
```

### 2. Start Dependencies (Postgres + Kafka + UI)
```bash
docker compose up -d
```

This runs:
- **Postgres** on `localhost:5432`
- **Kafka Broker** on `localhost:9094` (host listener)
- **Kafka UI** at `http://localhost:8081`

### 3. Run the Spring Boot Application
```bash
./mvnw spring-boot:run
```

---

## 🔑 REST Endpoints

### ➕ Create an Order
```http
POST http://localhost:8080/orders
Content-Type: application/json

{
  "customerEmail": "alice@example.com",
  "amount": 150.0,
  "currency": "USD"
}
```

Response:
```json
{
  "id": 1,
  "customerEmail": "alice@example.com",
  "amount": 150.0,
  "currency": "USD"
}
```

➡️ This also sends an `OrderCreatedEvent` to Kafka (`orders.v1`).

---

### 📜 Get All Orders
```http
GET http://localhost:8080/orders
```

---

## 📦 Kafka Topics
- **orders.v1** → Main topic where events are published.
- **orders.v1.dlt** → Dead-letter topic for failed messages.

You can explore these topics in **Kafka UI** (`http://localhost:8081`).

---

## 🛠️ Project Structure
```
src/main/java/com/faxriafrasiyab/reliable/orders
├── config/         # KafkaConfig, AppTopics
├── consumer/       # OrderConsumer
├── controller/     # OrderController
├── dto/            # OrderDto
├── entity/         # Order
├── events/         # OrderCreatedEvent
├── producer/       # OrderProducer
├── repository/     # OrderRepository
└── service/        # OrderService
```

---

## ⚡ Next Steps
- Add **NotificationService** (simulate sending emails after consuming events).
- Add **Integration Tests** with Testcontainers (Kafka + Postgres).
- Add **Monitoring** (Micrometer + Prometheus + Grafana).

---

💡 Author: **Fakhri Afrasiyabov**
