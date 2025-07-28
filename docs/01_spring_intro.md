# 🟢 01 - Introduction to Spring & Spring Boot

## What is the Spring Framework?

Spring is a powerful and flexible open-source framework for building Java applications. It provides comprehensive infrastructure support for developing robust backend systems, and is especially popular for web applications and enterprise-level software.

### 🔧 Core Features of Spring Framework:
- **Dependency Injection (DI):** Makes components loosely coupled and easier to test.
- **Aspect-Oriented Programming (AOP):** Helps modularize cross-cutting concerns like logging and security.
- **Transaction Management:** Abstracts transaction handling and works seamlessly with different data sources.
- **Integration with Other Technologies:** Supports integration with JDBC, JPA, JMS, etc.

---

## What is Spring Boot?

Spring Boot is an extension of the Spring Framework that simplifies application development by:
- Eliminating boilerplate configuration
- Providing default setups for many features
- Allowing quick project bootstrapping with embedded servers (e.g., Tomcat)
- Enabling rapid development of stand-alone applications

### 🚀 Key Advantages of Spring Boot:
- **Auto-configuration:** Automatically sets up application based on dependencies in the classpath.
- **Embedded servers:** Run your app directly without external server deployment.
- **Production-ready features:** Built-in metrics, health checks, and externalized configuration.
- **Convention over configuration:** Sensible defaults help reduce development time.

---

## Spring vs. Spring Boot

| Feature               | Spring Framework          | Spring Boot                      |
|----------------------|---------------------------|----------------------------------|
| Setup                | Manual, XML or Java config | Auto-configured                  |
| Server               | Requires external server    | Embedded (Tomcat, Jetty, etc.)   |
| Learning Curve       | Steep                      | Easier to get started            |
| Best For             | Large, complex systems     | Microservices, REST APIs         |

---

## Why Use Spring Boot?

Spring Boot is ideal when you want to:
- Build REST APIs quickly
- Develop microservices
- Avoid repetitive configuration
- Deploy standalone applications
