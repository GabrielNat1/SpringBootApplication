# Diagrams

## UML Class Diagram

*Insert UML diagram showing entities and relationships here.*

## Request Flow

```mermaid
sequenceDiagram
    participant Client
    participant Controller
    participant Service
    participant Repository
    participant Database

    Client->>Controller: HTTP Request
    Controller->>Service: Call business logic
    Service->>Repository: Data access
    Repository->>Database: Query/Update
    Database-->>Repository: Result
    Repository-->>Service: Data
    Service-->>Controller: Response
    Controller-->>Client: HTTP Response
```

## Layered Architecture

*Insert layered architecture diagram here.*

## Database Schema

*Insert ER diagram or table relationships here.*
