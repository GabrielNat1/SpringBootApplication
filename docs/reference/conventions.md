# API Conventions

- **Base URL:** `/api/v1/`
- **Authentication:** JWT via `Authorization: Bearer <token>`
- **Request/Response Format:** JSON
- **Date Format:** `YYYY-MM-DD`
- **Time Format:** `HH:mm:ss`
- **Error Response Example:**
  ```json
  {
    "timestamp": "2024-06-01T12:34:56",
    "status": 400,
    "error": "Bad Request",
    "message": "Invalid input",
    "path": "/api/v1/resource"
  }
  ```
- **Pagination:** Use `page` and `size` query parameters.
