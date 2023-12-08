# Todo List Application

This is a simple Todo List application built with the following frameworks:

- Spring Boot
- Hibernate
- MySQL
- Spring Security (for authentication)
- JWT (JSON Web Tokens) for securing the RESTful API
## Table of Contents

- [Prerequisites](#prerequisites)
- [Configuration](#configuration)
- [Database Setup](#database-setup)
- [Build and Run](#build-and-run)
- [Usage](#usage)

## Prerequisites

Ensure you have the following installed:

- JDK 17
- MySQL Database 8

## Configuration

Open the `src/main/resources/application.properties` file and configure the following properties:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todo
spring.datasource.username=<your_database_username>
spring.datasource.password=<your_database_password>

spring.jpa.hibernate.ddl-auto=update

security.jwt.secretKey=<your_jwt_secret_key>

user.username=<your_user_username>
user.password=<your_hmac256_encrypted_password>
```
Replace <your_database_username>, <your_database_password>, <your_jwt_secret_key>, <your_user_username>, and <your_hmac256_encrypted_password> with your actual configuration.

Note: The user.password property should be HMAC256 encrypted.

## Database Setup

Create a MySQL database named todo or update the spring.datasource.url property accordingly. The application will automatically update the database schema based on the entities.

## Build and Run

Navigate to the project root directory and run the following commands:

```
./mvnw clean install
java -jar target/todo-0.0.1-SNAPSHOT.jar
```
## Usage
Below is an example of how you can use Curl to interact with the provided RESTful API for Task management. Note that for all operations except /auth/login, authorization using the Bearer token is required.

Task Management API
#### Get Bearer Token
```bash
curl -X POST -H "Content-Type: application/json" -d '{"username": "your_username", "password": "your_password"}' http://localhost:8080/auth/login
```
Replace your_username and your_password with your actual credentials.

#### Get All Tasks
```bash
Copy code
curl -X GET -H "Authorization: Bearer your_token" http://localhost:8080/api/tasks/
```
Replace your_token with the token obtained from the login request.

#### Get Task by ID
```bash
curl -X GET -H "Authorization: Bearer your_token" http://localhost:8080/api/tasks/{id}
```
Replace your_token and {id} with the token obtained from the login request and the actual task ID.

#### Create Task
```bash
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer your_token" -d '{"title": "New Task", "completed": false}' http://localhost:8080/api/tasks/
```
Replace your_token with the token obtained from the login request.

#### Update Task
```bash
curl -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer your_token" -d '{"title": "Updated Task", "completed": true}' http://localhost:8080/api/tasks/{id}
```
Replace your_token, {id}, and the request body with the token obtained from the login request, the actual task ID, and the updated task details.

#### Delete Task
```bash
curl -X DELETE -H "Authorization: Bearer your_token" http://localhost:8080/api/tasks/{id}
```
Replace your_token and {id} with the token obtained from the login request and the actual task ID.

#### Get Completed Tasks
```bash
curl -X GET -H "Authorization: Bearer your_token" http://localhost:8080/api/tasks/completed
```
Replace your_token with the token obtained from the login request.

Make sure to replace placeholders like your_username, your_password, your_token, and {id} with your actual values.
