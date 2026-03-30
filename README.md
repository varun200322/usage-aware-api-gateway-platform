# Usage-Aware Reactive API Gateway Platform

A production-style backend system built using Spring Boot and Spring Cloud Gateway that demonstrates secure API routing, distributed rate limiting, usage metering, billing aggregation, and containerized microservices orchestration.

---

## Overview

This project simulates a real-world backend architecture where:

* A gateway validates requests using JWT
* Applies tenant-aware rate limiting using Redis
* Routes requests to downstream services
* Tracks usage events
* Generates billing summaries

---

## Services

### gateway-service

* Entry point for all requests
* JWT authentication
* Rate limiting (Redis)
* Routing to downstream services
* Retry + Circuit Breaker (Resilience4j)
* Usage logging

### orders-service

* Sample downstream service
* Provides order APIs

### usage-service

* Stores usage events
* Used for analytics and billing

### billing-service

* Aggregates usage data
* Generates billing summaries per tenant

### redis

* Stores distributed rate limit counters

---

## Tech Stack

* Java 17
* Spring Boot
* Spring Cloud Gateway
* Spring Security (JWT)
* WebFlux (Reactive Programming)
* Redis
* Resilience4j
* Docker
* Docker Compose
* Maven

---

## Project Structure

```
api-gateway/
├── gateway-service/
├── orders-service/
├── user-service/
├── billing-service/
├── docker-compose.yml
└── README.md
```

---

## How to Run (Docker Compose)

### Prerequisites

* Java 17
* Maven
* Docker Desktop

---

### Step 1: Build all services

Run inside each service folder:

```
mvn clean package
```

---

### Step 2: Start the full system

From the root folder:

```
docker compose up --build
```

This will:

* Build all Docker images
* Start all services
* Create a shared Docker network
* Enable service-to-service communication

---

### Step 3: Verify services

Open in browser or Postman:

* Gateway: http://localhost:8080/actuator/health
* Orders: http://localhost:8081/api/orders/1
* Usage: http://localhost:8082/usage
* Billing: http://localhost:8083/billing/summary/pro

---

## Important Concept

* `localhost` works only from your machine
* Inside Docker, services communicate using service names

Examples:

* Gateway → Orders
  `http://orders-service:8081`

* Gateway → Usage
  `http://usage-service:8082`

* Billing → Usage
  `http://usage-service:8082`

* Redis host
  `redis`

---

## Example Flow

1. Client sends request with JWT to gateway
2. Gateway validates token
3. Gateway applies tenant-based rate limiting
4. Request routed to orders-service
5. Gateway logs usage to usage-service
6. Billing-service aggregates usage and computes cost

---

## Example Endpoints

### Direct Service Access

* `GET http://localhost:8081/api/orders/1`
* `GET http://localhost:8082/usage`
* `GET http://localhost:8083/billing/summary/pro`

---

### Gateway Access (JWT Required)

* `GET http://localhost:8080/api/orders/1`

---

## JWT Testing (Postman)

### Step 1: Generate Token

Use your token generation logic and copy the JWT.

---

### Step 2: Add Header in Postman

```
Authorization: Bearer <your_token>
```

---

### Step 3: Send Request

```
GET http://localhost:8080/api/orders/1
```

---

## Features Implemented

- Reactive API gateway using Spring Cloud Gateway and WebFlux
- JWT-secured request flow
- Redis-backed tenant-aware rate limiting
- Circuit breaker, retry, and fallback using Resilience4j
- Usage tracking service
- Billing aggregation service
- Dockerized multi-service architecture
- Docker Compose orchestration
- CI pipeline using GitHub Actions
- Docker image build automation in CI
- Static code analysis using SonarCloud

---

## Key Concepts Demonstrated

* Microservices architecture
* API Gateway pattern
* Reactive programming
* Distributed rate limiting
* Inter-service communication (Docker networking)
* Fault tolerance (Resilience4j)
* Containerization

---

## Future Enhancements

- JUnit and Mockito test coverage across all services
- Persistent database storage for usage and billing data
- OpenAPI / Swagger documentation
- Image publishing and deployment automation
- Multi-plan billing rules and invoice generation

---

## Author

Varun Nagesh Reddy
