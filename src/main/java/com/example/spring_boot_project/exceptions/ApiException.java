package com.example.spring_boot_project.exceptions;

public abstract class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}