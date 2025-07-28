# 🟢 02 - Creating a Project with Spring Initializr

## 📦 What is Spring Initializr?

Spring Initializr is an online project generator that helps developers create a pre-configured Spring Boot project structure with the desired dependencies. It's the easiest and fastest way to bootstrap a new Spring Boot application.

---

## 🌐 Accessing Spring Initializr

You can access it via:
- Website: [https://start.spring.io](https://start.spring.io)
- IntelliJ IDEA (built-in Spring project wizard)

---

## 🛠️ Creating a Project via Web (https://start.spring.io)

### ✅ Steps:

1. **Project**: Select `Maven` or `Gradle` (use Maven for this course).
2. **Language**: Choose `Java`.
3. **Spring Boot Version**: Select a stable version (e.g., `3.2.x`).
4. **Project Metadata**:
    - *Group*: `com.example`
    - *Artifact*: `springbootapplicationcourse`
    - *Name*: `springbootapplicationcourse`
    - *Package name*: `com.example.springbootapplicationcourse`
5. **Packaging**: `Jar`
6. **Java Version**: Choose `17` or later.
7. **Dependencies**: Add the following:
    - `Spring Web`
    - `Spring Data JPA`
    - `PostgreSQL Driver`
    - `Spring Security` (optional for now)

8. Click **"Generate"** to download the `.zip` project.

9. Extract the ZIP and open it in your IDE (e.g., IntelliJ).

---

## 💡 Creating a Project via IntelliJ IDEA

1. Go to **File > New > Project**
2. Select **Spring Initializr**
3. Configure the same settings as above.
4. IntelliJ will generate and open the project automatically.

---

## 📁 Generated Project Structure

```plaintext
springbootapplicationcourse/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/springbootapplicationcourse/
│   │   │       └── SpringbootapplicationcourseApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/
│       └── java/...
├── pom.xml
└── README.md
```