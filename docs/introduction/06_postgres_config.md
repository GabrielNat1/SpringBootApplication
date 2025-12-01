# 🟢 06 - PostgreSQL Setup and Configuration

---

## 🔧 Configuration Options

You can configure your database credentials either via environment variables (recommended) or directly in `application.properties`.

---

### ⚡ Option 1: Using `.env` file with Dotenv (recommended)

#### 1. Create a `.env` file in your project root (example `.env.example`):

```editorconfig
# .env.example

# Application name
SPRING_APPLICATION_NAME=spring-boot-project

# Database configuration
DB_URL=jdbc:postgresql://localhost:5432/springbootdb
DB_USERNAME=springuser
DB_PASSWORD=springpassword
```

#### 2. Create a config class to load `.env` variables

Place this file under `src/main/java/com/example/spring_boot_project/config/DotenvInitializer.java`:

```java
package com.example.spring_boot_project.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvInitializer {
public static Dotenv dotenv;

    @PostConstruct
    public static void init(){
        try {
            dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();

            System.out.println("Dotenv loaded");
        } catch (DotenvException e) {
            throw new RuntimeException(e);
        }
    }
}
```

> This will load the `.env` file and make variables available via `DotenvInitializer.dotenv`.

#### 3. Initialize Dotenv in your main application class

In `src/main/java/com/example/spring_boot_project/SpringBootProjectApplication.java`:

```java
package com.example.spring_boot_project;

import com.example.spring_boot_project.config.DotenvInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootProjectApplication {
public static void main(String[] args) {
DotenvInitializer.init();
SpringApplication.run(SpringBootProjectApplication.class, args);
}
}
```

#### 4. Access environment variables anywhere in your code like:

```java
String dbUrl = DotenvInitializer.dotenv.get("DB_URL");
```

You can then configure your datasource programmatically or pass these variables to Spring.

---

### ⚡ Option 2: Using `application.properties`

Alternatively, configure your database connection directly in `src/main/resources/application.properties`:

```editorconfig
spring.datasource.url=jdbc:postgresql://localhost:5432/springbootdb
spring.datasource.username=springuser
spring.datasource.password=springpassword
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---