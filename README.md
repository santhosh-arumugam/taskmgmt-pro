# Task Manager Pro

## Overview
Task Manager Pro is a robust Task Management System built with **Spring Boot**, designed for team collaboration. It supports CRUD operations for `Users`, `Projects`, and `Tasks`, with tasks assignable to users and linked to projects. This project showcases my expertise in RESTful API development, Spring Data JPA, Spring Security and clean code practices.

## Features
- **CRUD Operations**:
  - Manage `Users`, `Projects`, and `Tasks` with full create, read, update, and delete functionality.
- **Pagination & Sorting**:
  - Paginated API responses (e.g., GET /tasks?page=0&size=10).
  - Sorting by fields like createdAt, priority, or status.
- **Data Validation**: Ensures valid inputs (e.g., unique username,valid email format, non-empty task title).
- **Error Handling**: Clear error messages with appropriate HTTP status codes (e.g., `404` for non-existent resources).
- **Timestamps**: Auto-updates `createdAt` and `updatedAt` for `Tasks` using `@PreUpdate` and `@CreatedDate`.
- **Partial Updates**: Supports `PATCH` for updating specific fields (e.g., task title or user email).
- **Database Integration**: Uses **Spring Data JPA** with **PostgreSQL** for persistent storage.
- **Testing**: Comprehensive unit tests with **JUnit** and **Mockito**.

## Technologies Used
- **Backend**: [Spring Boot](https://spring.io/projects/spring-boot), [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- **Database**: [PostgreSQL](https://www.postgresql.org/)
- **Security**: [Spring Security](https://spring.io/projects/spring-security)
- **Mapping**: [MapStruct](https://mapstruct.org/)
- **Validation**: [Jakarta Bean Validation](https://beanvalidation.org/)
- **Testing**: [JUnit](https://junit.org/junit5/), [Mockito](https://site.mockito.org/)
- **Build Tool**: [Maven](https://maven.apache.org/)
- **Others**: [Lombok](https://projectlombok.org/), [SLF4J](https://www.slf4j.org/) for logging

## Project Structure
```
taskmgmt-pro/
├── src/main/java/com/development/taskmgmt_pro/
│   ├── controller/      # REST controllers
│   ├── service/         # Business logic
│   ├── repository/      # JPA repositories
│   ├── model/           # Entity classes
│   ├── dto/             # Data Transfer Objects
│   ├── mapper/          # MapStruct mappers
│   ├── enums/           # Enum classes
│   ├── exception/       # Custom exceptions
│   ├── config/          # Audit and Security Configs
├── src/test/java/       # Unit tests
├── pom.xml              # Maven dependencies
├── application.properties # Configuration
└── README.md
```

## Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/santhosh-arumugam/taskmgmt-pro.git
   cd taskmgmt-pro
   ```

2. **Configure Database**:
   - Install [PostgreSQL](https://www.postgresql.org/download/) and create a database:
     ```sql
     CREATE DATABASE taskmgmt_pro;
     ```
   - Update `src/main/resources/application.properties`:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/taskmgmt_pro
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
     ```

3. **Build and Run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   - Access the API at `http://localhost:8080`.

4. **Test APIs**:
   - Use [Postman](https://www.postman.com/) or `cURL` to test endpoints (see below).
   - Example:
     ```bash
     curl -X POST http://localhost:8080/users \
     -H "Content-Type: application/json" \
     -d '{"userName": "john_doe", "emailId": "john@example.com", "fullName": "John Doe", "jobRole": "DEVELOPER"}'
     ```

## API Endpoints

### User
- `POST /users`: Create a user.
- `GET /users`: List all users.
- `GET /users/{id}`: Get user by ID.
- `PUT /users/{id}`: Full update of user details.
- `DELETE /users/{id}`: Delete a user.

### Project
- `POST /projects`: Create a project.
- `GET /projects`: List all projects.
- `GET /projects/{id}`: Get project by ID.
- `PUT /projects/{id}`: Update project details.
- `DELETE /projects/{id}`: Delete a project.

### Task
- `POST /tasks`: Create a task.
- `GET /tasks`: List all tasks.
- `GET /tasks/{id}`: Get task by ID.
- `PUT /tasks/{id}`: Full update of task details.
- `DELETE /tasks/{id}`: Delete a task.

## Example API Usage
- **Create User**:
  ```bash
  curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"userName": "john_doe", "emailId": "john@example.com", "fullName": "John Doe", "jobRole": "DEVELOPER"}'
  ```
- **Delete Task**:
  ```bash
  curl -X DELETE http://localhost:8080/tasks/1 \
  -H "Content-Type: application/json"
  ```

## Database Schema
- **user**: `id`, `name`, `email`, `full_name`, `job_role`, `created_at`
- **project**: `id`, `name`, `description`, `start_date`, `end_date`, `status`
- **task**: `id`, `title`, `description`, `priority`, `status`, `created_at`, `updated_at`, `task_id`, `project_id`

## Testing
- Run unit tests:
  ```bash
  mvn test
  ```
- Tests cover controllers, services, and repositories, including edge cases like non-existent resources.

## Showcase
This project highlights my skills in:
- Building **RESTful APIs** with [Spring Boot](https://spring.io/projects/spring-boot).
- Managing **entity relationships** (One-to-Many, Many-to-One) using [Spring Data JPA](https://spring.io/projects/spring-data-jpa).
- Implementing pagination and sorting for efficient data retrieval.
- Using enums for type-safe fields (e.g., JobRole, TaskStatus, TaskPriority, ProjectStatus).
- Implementing **data validation** and **error handling** for robust APIs.
- Writing **clean, maintainable code** with separation of concerns.
- Creating **unit tests** for reliable code coverage.
- Integrating with **PostgreSQL** for persistent storage.

Check out the [source code](https://github.com/santhosh-arumugam/taskmgmt-pro) to explore my implementation, or run the project to interact with the APIs!

## Future Improvements
- Add **filtering** for large datasets.
- Implement **[Spring Security](https://spring.io/projects/spring-security)** for role-based access.
- Set up **CI/CD** with [GitHub Actions](https://github.com/features/actions).
- Generate **API documentation** with [Swagger](https://swagger.io/).

## Contact
For feedback or collaboration:
- **GitHub**: [santhosh-arumugam](https://github.com/santhosh-arumugam)
- **Email**: itsforprofession@gmail.com