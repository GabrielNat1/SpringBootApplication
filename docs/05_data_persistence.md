# 🟢 05 - Data Persistence with Spring Data JPA

## What is Spring Data JPA?

Spring Data JPA is a part of the Spring Framework that simplifies database access by implementing the JPA (Java Persistence API) specification. It allows developers to perform CRUD (Create, Read, Update, Delete) operations with minimal boilerplate code.

---

## 🏗️ Core Concepts

- **Entity**: A Java class mapped to a database table.
- **Repository**: Interface that handles data access and queries.
- **JpaRepository**: A Spring Data interface that provides basic CRUD methods and pagination.
- **EntityManager**: Core JPA interface for interacting with the persistence context.

---

## 📁 Creating an Entity

```java
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    // getters and setters
}
```

---

## 📁 Creating a Repository Interface

Extend `JpaRepository` to get CRUD methods automatically.

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
// Custom queries can be added here if needed
}
```