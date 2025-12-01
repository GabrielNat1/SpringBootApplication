# 🟢 07 - Product API

Documentation for the REST API to manage products, structured in layers: model, repository, service, and controller.

---

## Model

**Product** entity with fields `id`, `name`, and `price`.

```java
package com.example.spring_boot_project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private Double price;

    // Constructors, getters, and setters
}
```

---

## Repository

**ProductRepository** interface extending `JpaRepository` for CRUD operations.

```java
package com.example.spring_boot_project.repository;

import com.example.spring_boot_project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}
```

---

## Service

**ProductService** class with CRUD methods for products.

```java
package com.example.spring_boot_project.service;

import com.example.spring_boot_project.model.Product;
import com.example.spring_boot_project.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() { return productRepository.findAll(); }
    public static Optional<Product> getProductById(Long id) { return productRepository.findById(id); }
    public Product saveProduct(Product product) { return productRepository.save(product); }
    public void deleteProduct(Long id) { productRepository.deleteById(id); }
}
```

---

## Controller

Exposes REST endpoints:

- `GET /api/products/list` — lists all products
- `GET /api/products/{id}` — get product by ID
- `POST /api/products` — create product
- `DELETE /api/products/{id}` — delete product

```java
package com.example.spring_boot_project.controller;

import com.example.spring_boot_project.model.Product;
import com.example.spring_boot_project.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
private final ProductService productService;

    public ProductController(ProductService productService) { this.productService = productService; }

    @GetMapping("/list")
    public List<Product> getAllProducts() { return productService.getAllProducts(); }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ProductService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) { return productService.saveProduct(product); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
```

---