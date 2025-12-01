# 🌍 Global Exception Handling in Spring Boot

## 📌 Overview
Global exception handling provides a centralized way to manage exceptions across your entire Spring Boot application.

## 🏗 Key Components
- `@ControllerAdvice`: Annotation for global exception handling
- `@ExceptionHandler`: Method-level annotation for specific exceptions
- `ResponseEntity`: For custom HTTP responses

## 🛠 Basic Implementation
```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
        ResourceNotFoundException ex) {
        
        ErrorResponse error = new ErrorResponse(
            "NOT_FOUND",
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value());
            
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
```

## 📝 Error Response Class
```java
public class ErrorResponse {
private String code;
private String message;
private int status;

    // Constructor, getters and setters
}
```

## 🎯 Handling Multiple Exceptions

### Option 1: Separate Methods
```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ErrorResponse> handleValidationErrors(
MethodArgumentNotValidException ex) {
// Handle validation errors
}

@ExceptionHandler(AccessDeniedException.class)
public ResponseEntity<ErrorResponse> handleAccessDenied(
AccessDeniedException ex) {
// Handle access denied
}
```

### Option 2: Single Method with Parent Exception
```java
@ExceptionHandler({Exception.class, RuntimeException.class})
public ResponseEntity<ErrorResponse> handleGenericExceptions(
Exception ex) {
// Handle generic exceptions
}
```

## 🔍 Best Practices

1. **Organize by Exception Type**:
    - Create separate `@ControllerAdvice` classes for different exception categories

2. **Standardize Responses**:
    - Use consistent error response structures

3. **Log Exceptions**:
    - Always log exceptions before handling

4. **Handle Spring Specific Exceptions**:
    - `MethodArgumentNotValidException`
    - `HttpRequestMethodNotSupportedException`
    - `HttpMediaTypeNotSupportedException`

5. **Security Considerations**:
    - Don't expose sensitive information in error responses

## 🏹 Custom Business Exceptions
```java
public class BusinessException extends RuntimeException {
private String errorCode;

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    // Getters
}

// Usage:
throw new BusinessException("Invalid order status", "ORD-001");
```

## 📚 Advanced Topics

### 1. Response Status Codes
- Use `@ResponseStatus` annotation for default HTTP status

### 2. Validation Errors
- Special handling for `@Valid` failures

### 3. Internationalization
- Support for localized error messages

### 4. Error Codes
- Standardized error code system

### 5. Problem Details (RFC 7807)
- Standardized error response format