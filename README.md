<div align="center">

# Spring Boot Application Course

![Java](https://img.shields.io/badge/Java-17%2B-blue.svg?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F.svg?logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-Enabled-6DB33F.svg?logo=spring-security&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-ORM-6DB33F.svg?logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36.svg?logo=apache-maven&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Auth-000.svg?logo=jsonwebtokens&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-Database-07405E.svg?logo=sqlite&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-Cache%2FVPN%20Detection-D92B2B.svg?logo=redis&logoColor=white)

</div>

---

This repository contains a structured Spring Boot application developed step-by-step, following modern backend development practices. The project includes database integration, error handling, authentication, and user security using JWT.

---

## 🧱 Project Overview

This application was built to explore and learn key concepts in the Spring ecosystem:

- 🌱 Spring Framework & Spring Boot fundamentals
- ⚡ Creating projects with Spring Initializr
- 🛠️ Using profiles in IntelliJ
- 🗂️ Spring Boot project structure
- 💾 Persistence with Spring Data JPA
- 🐘 PostgreSQL configuration
- 🔗 RESTful Product API
- 🚨 Global and custom exception handling
- 🔒 Authentication and security with Spring Security and JWT

---

<details>
<summary> <h1> <strong> 📚 Documentation V1 </strong> </summary>

---

All documentation is organized by topic for easy navigation:

### 📖 Introduction
> - Essential fundamentals to get started with Spring and set up the environment.

- 📖 [01_spring_intro.md](docs/introduction/01_spring_intro.md) — Spring & Spring Boot Introduction  
- 📖 [02_initializr_project.md](docs/introduction/02_initializr_project.md) — Creating a project with Spring Initializr  
- 📖 [03_profiles_intellij.md](docs/introduction/03_profiles_intellij.md) — IntelliJ profile configuration  
- 📖 [04_structure.md](docs/introduction/04_structure.md) — Basic project structure  
- 📖 [05_data_persistence.md](docs/introduction/05_data_persistence.md) — Data persistence with Spring Data JPA  
- 📖 [06_postgres_config.md](docs/introduction/06_postgres_config.md) — PostgreSQL setup  
- 📖 [07_product_api.md](docs/introduction/07_product_api.md) — Creating the Product API  
- 📖 [08_exception_handling.md](docs/introduction/08_exception_handling.md) — Exception handling  
- 📖 [09_global_exceptions.md](docs/introduction/09_global_exceptions.md) — Global exception handling  
- 📖 [10_security_jwt.md](docs/introduction/10_security_jwt.md) — Authentication & Security (JWT)  
- 📖 [11_user_auth_routes.md](docs/introduction/11_user_auth_routes.md) — User authentication and route protection  

### ⚙️ Setup
> - Prepare the environment to run the project.

- ⚙️ [prerequisites.md](docs/setup/prerequisites.md) — Prerequisites  
- ⚙️ [installation.md](docs/setup/installation.md) — Installation  
- ⚙️ [environment-variables.md](docs/setup/environment-variables.md) — Environment Variables  
- ⚙️ [running-locally.md](docs/setup/running-locally.md) — Running Locally  

### 🏛️ Architecture
> - Structure, layers, and patterns of the application.

- 🏛️ [layers.md](docs/architecture/layers.md) — Architecture Layers  
- 🏛️ [modules.md](docs/architecture/modules.md) — Modules  
- 🏛️ [domain-model.md](docs/architecture/domain-model.md) — Domain Model  
- 🏛️ [rest-api-structure.md](docs/architecture/rest-api-structure.md) — REST API Structure  
- 🏛️ [diagrams.md](docs/architecture/diagrams.md) — Diagrams  

### 🚀 Features
> - Main features that make up the application.

- 🚀 [authentication.md](docs/features/authentication.md) — Authentication  
- 🚀 [products.md](docs/features/products.md) — Products  
- 🚀 [cart.md](docs/features/cart.md) — Cart  
- 🚀 [captcha.md](docs/features/captcha.md) — Captcha  

### 📦 API Reference
> - API routes, patterns, and responses.

- 📦 [endpoints.md](docs/api/endpoints.md) — Endpoints  
- 📦 [request-response-structure.md](docs/api/request-response-structure.md) — Request/Response Structure  
- 📦 [error-handling.md](docs/api/error-handling.md) — Error Handling  
- 📦 [pagination.md](docs/api/pagination.md) — Pagination  
- 📦 [versioning.md](docs/api/versioning.md) — Versioning  

### 📑 Reference
> - Codes, conventions, and data formats.

- 📑 [status-codes.md](docs/reference/status-codes.md) — HTTP Status Codes  
- 📑 [common-errors.md](docs/reference/common-errors.md) — Common Errors  
- 📑 [conventions.md](docs/reference/conventions.md) — API Conventions  
- 📑 [data-types.md](docs/reference/data-types.md) — Data Types   

### 🛡️ Security
> - Protection, authentication, and security mechanisms.

- 🛡️ [jwt.md](docs/security/jwt.md) — JWT Authentication  
- 🛡️ [captcha.md](docs/security/captcha.md) — Captcha  
- 🛡️ [RateLimitingFilter.md](docs/security/RateLimitingFilter.md) — Rate Limiting  
- 🛡️ [LoggingInterceptor.md](docs/security/LoggingInterceptor.md) — Logging  
- 🛡️ [vpn.md](docs/security/vpn.md) — VPN/Proxy Considerations  

### 🛰️ VPN Advanced
> - Advanced VPN filtering and analysis tools.

- 🛰️ [overview.md](docs/package-vpn-advanced/overview.md) — VPN Advanced Overview  
- 🛰️ [checker.md](docs/package-vpn-advanced/checker.md) — VPN Checker  
- 🛰️ [risk.md](docs/package-vpn-advanced/risk.md) — VPN Risk Service  
- 🛰️ [monitor.md](docs/package-vpn-advanced/monitor.md) — VPN Monitor  
- 🛰️ [filter.md](docs/package-vpn-advanced/filter.md) — Anti-VPN Filter  
- 🛰️ [status.md](docs/package-vpn-advanced/status.md) — VPN Status Enum  
- 🛰️ [config.md](docs/package-vpn-advanced/config.md) — VPN Config  
- 🛰️ [integration.md](docs/package-vpn-advanced/integration.md) — Integration Guide  
- 🛰️ [redis.md](docs/package-vpn-advanced/redis.md) — Redis & ReactiveRedis  

### ⚙️ Operations / DevOps
> - Monitoring, logs, metrics, and system maintenance.

- ⚙️ [monitoring.md](docs/operations/monitoring.md) — Monitoring  
- ⚙️ [logging-structure.md](docs/operations/logging-structure.md) — Logging Structure  
- ⚙️ [metrics.md](docs/operations/metrics.md) — Metrics  
- ⚙️ [health-checks.md](docs/operations/health-checks.md) — Health Checks  
- ⚙️ [troubleshooting.md](docs/operations/troubleshooting.md) — Troubleshooting  
- ⚙️ [ci-cd.md](docs/operations/ci-cd.md) — CI/CD  
</details>

---

<details>
<summary> <h1> <strong> 📚 Documentation V2 </strong> </summary>

---

> <strong>future documentation v2

</details>

---

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

---

## 📄 License

This project is licensed under the MIT License.
