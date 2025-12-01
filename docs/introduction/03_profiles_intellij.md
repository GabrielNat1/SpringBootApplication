# 🟢 03 - IntelliJ Profile Configuration (Spring Profiles)

## 🎯 What are Spring Profiles?

Spring Profiles allow you to define different configurations for different environments (e.g., development, test, production). Each profile can have its own properties, beans, and behavior.

This is useful for:
- Setting different databases for dev/prod
- Using specific logging settings
- Avoiding manual reconfiguration every time you change environments

---

## 📁 Profile Files in Spring Boot

You can create multiple configuration files:

- `application.properties` (default)
- `application-dev.properties` (for development)
- `application-prod.properties` (for production)
- `application-test.properties` (for testing)

---