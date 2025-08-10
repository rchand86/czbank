# Spring Boot + MySQL (Docker Only)

This project runs a Spring Boot application with MySQL using **only Docker**.  
No Maven or Java installation is required on your machine.

The API will be available at:  
**http://localhost:9090**

---

## 📦 Requirements
- Docker
- Docker Compose

---

## 🚀 Run the Application

1. **Start containers**
   ```bash
   docker-compose up --build

Test the API

http://localhost:9090/api/credit-rating?customerId=14&customerRating=GOOD

Stop the Application

docker-compose down