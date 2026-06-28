# Finance Tracker API

A REST API built with Spring Boot for tracking personal income and expenses.

## Tech Stack
- Java 25
- Spring Boot
- Spring Security (Session-based authentication)
- Spring Data JPA
- PostgreSQL
- Docker

## Features
- User registration and authentication
- Create, read, and delete transactions
- Filter transactions by description, date range, and pagination
- Monthly income/expense summary

## Running locally

### Prerequisites
- Docker Desktop

### Steps
1. Clone the repository
2. Start the database:
```bash
docker compose up -d
```
3. Run the Spring Boot app with the `dev` profile active

The API will be available at `http://localhost:8080/api/v1`

## API Endpoints

| Method | Endpoint                     | Description | Auth required |
|--------|------------------------------|-------------|---------------|
| POST | /api/v1/users                | Register a new user | No |
| POST | /api/v1/users/login          | Login | No |
| POST | /api/v1/users/logout         | Logout | Yes |
| GET | /api/v1/users/me             | Get current user | Yes |
| DELETE | /api/v1/users             | Deletes logged-in user | Yes |
| GET | /api/v1/transactions         | Search transactions | Yes |
| POST | /api/v1/transactions         | Create transaction | Yes |
| DELETE | /api/v1/transactions/{id}    | Delete transaction | Yes |
| GET | /api/v1/transactions/summary | Get monthly summary | Yes |

