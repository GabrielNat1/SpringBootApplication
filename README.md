# Spring Boot Application Course

This repository contains a structured Spring Boot application developed step-by-step, following modern backend development practices. The project includes database integration, error handling, authentication, and user security using JWT.

## 🧱 Project Overview

This application was built to explore and learn key concepts in the Spring ecosystem:

- Spring Framework & Spring Boot fundamentals
- Creating projects with Spring Initializr
- Using profiles in IntelliJ
- Spring Boot project structure
- Persistence with Spring Data JPA
- PostgreSQL configuration
- RESTful Product API
- Global and custom exception handling
- Authentication and security with Spring Security and JWT

## 📁 Documentation

Each part of the course is explained in its own markdown file:

- 🟢 [**01_spring_intro.md**](docs/01_spring_intro.md): Introduction to Spring & Spring Boot  
- 🟢 [**02_initializr_project.md**](docs/02_initializr_project.md): Creating a project with Spring Initializr  
- 🟢 [**03_profiles_intellij.md**](docs/03_profiles_intellij.md): IntelliJ profile configuration  
- 🟢 [**04_structure.md**](docs/04_structure.md): Basic project structure  
- 🟢 [**05_data_persistence.md**](docs/05_data_persistence.md): Data persistence with Spring Data JPA  
- 🟢 [**06_postgres_config.md**](docs/06_postgres_config.md): PostgreSQL setup  
- 🟢 [**07_product_api.md**](docs/07_product_api.md): Creating the Product API  
- 🟢 [**08_exception_handling.md**](docs/08_exception_handling.md): Exception handling in Spring Boot  
- 🟢 [**09_global_exceptions.md**](docs/09_global_exceptions.md): Global exception handling  
- 🟢 [**10_security_jwt.md**](docs/10_security_jwt.md): Authentication & Security with Spring Security and JWT  
- 🟢 [**11_user_auth_routes.md**](docs/11_user_auth_routes.md): User authentication and route protection  

## 🚀 Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- Maven

## 🛠️ How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/GabrielNat1/springbootapplicationcourse.git
   cd springbootapplicationcourse
   ```
   
2. Configure your PostgreSQL database in env.

3. Run the project:
  ```bash
  ./mvnw spring-boot:run
  ```
