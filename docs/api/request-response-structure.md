# Request & Response Structure

## Request

- Content-Type: `application/json`
- Authentication: JWT in `Authorization` header for protected endpoints

### Example Request

```http
POST /api/v1/resource
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Example",
  "description": "Sample resource"
}
```

## Response

- Content-Type: `application/json`
- Standard fields for error responses

### Example Success Response

```json
{
  "id": 1,
  "name": "Example",
  "description": "Sample resource"
}
```

### Example Error Response

```json
{
  "timestamp": "2024-06-01T12:34:56",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid input",
  "path": "/api/v1/resource"
}
```
