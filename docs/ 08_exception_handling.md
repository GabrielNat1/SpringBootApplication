# Java Exception Handling - Complete Guide

## 📌 What are Exceptions?
Abnormal events that disrupt the normal program flow. In Java, they are objects inheriting from `Throwable`.

## 🏗 Exception Hierarchy
```java
Throwable
├── Error (e.g., OutOfMemoryError)
└── Exception
├── RuntimeException (unchecked)
└── Checked Exceptions (e.g., IOException)
```

text

## 🚨 Common Exceptions
| Exception | When It Occurs |
|-----------|----------------|
| `NullPointerException` | Calling method on null object |
| `ArrayIndexOutOfBoundsException` | Accessing invalid array index |
| `IOException` | Input/Output errors |
| `ClassCastException` | Invalid type casting |
| `IllegalArgumentException` | Invalid method argument |

## 🛡 Basic Handling
```java
try {
FileReader file = new FileReader("file.txt");
} catch (FileNotFoundException e) {
System.out.println("File not found!");
e.printStackTrace();
} finally {
System.out.println("Always executes!");
}
```

## 🎯 Best Practices
1. Always catch specific exceptions
2. Use `finally` for resource cleanup
3. Document exceptions with `@throws`
4. Avoid generic `catch(Exception e)`
5. Consider custom exceptions

## 🏹 Custom Exceptions
```java
class InsufficientFundsException extends Exception {
public InsufficientFundsException(String message) {
super(message);
}
}

// Usage:
throw new InsufficientFundsException("Negative balance!");
```

## ⚖️ Checked vs Unchecked
- **Checked**: Must be handled (inherit from Exception)
- **Unchecked**: Optional to handle (inherit from RuntimeException)

## 📚 Additional Features
- `try-with-resources` (Java 7+)
- Multi-catch (Java 7+)
- `Optional` (Java 8+) to avoid NullPointerException