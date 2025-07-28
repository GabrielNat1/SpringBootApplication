# 🟢 04 - Basic Spring Boot Project Structure

## Overview

A typical Spring Boot project follows a well-organized structure that helps separate concerns and organize code for maintainability and scalability.

---

## 📁 Project Folder Layout

```plaintext
springbootapplicationcourse/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/springbootapplicationcourse/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── repository/
│   │   │       ├── model/
│   │   │       └── SpringbootapplicationcourseApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
│           └── com/example/springbootapplicationcourse/
│               └── (test classes)
├── pom.xml
```

---

## 📂 Explanation of Key Folders and Files

- **`controller/`**  
  Contains REST controllers that handle HTTP requests and responses.

- **`service/`**  
  Holds business logic and application services.

- **`repository/`**  
  Interfaces responsible for data access, often extending Spring Data JPA repositories.

- **`model/`**  
  Domain entities and data transfer objects (DTOs).

- **`SpringbootapplicationcourseApplication.java`**  
  The main class with the `main()` method that starts the Spring Boot application.

- **`resources/application.properties`**  
  Configuration file for application settings like database, ports, logging, etc.

- **`resources/static/`**  
  Folder to serve static files (JS, CSS, images).

- **`resources/templates/`**  
  Template files (e.g., Thymeleaf or other view engines) if you build a web UI.

- **`test/`**  
  Contains test classes for unit and integration tests.

---