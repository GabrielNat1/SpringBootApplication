# Architecture Layers

## Controller
Handles HTTP requests and responses. Defines API endpoints and delegates business logic to services.

## Service
Contains business logic and orchestrates operations between controllers and repositories.

## Repository
Responsible for data access and persistence. Interfaces with the database using JPA or other mechanisms.

## Domain / Entity
Represents core business objects (e.g., User, Product, Cart) mapped to database tables.

## Exceptions
Custom exception classes for error handling and validation.

## Config
Configuration classes for security, CORS, beans, and other application settings.
