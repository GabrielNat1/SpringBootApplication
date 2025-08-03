package com.example.spring_boot_project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFound ex){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Timestamp", LocalDate.now());
        body.put("Status", HttpStatus.NOT_FOUND.value());
        body.put("Error", "Resource Not Found");
        body.put("Message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(ResourceNotFound ex){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Timestamp", LocalDate.now());
        body.put("Status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("Error", "Error Internal Server Error");
        body.put("Message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
