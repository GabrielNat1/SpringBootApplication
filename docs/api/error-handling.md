# Error Handling

All errors are returned in a consistent JSON format.

## Error Response Structure

| Field     | Type    | Description                       |
|-----------|---------|-----------------------------------|
| timestamp | string  | Date and time of the error        |
| status    | integer | HTTP status code                  |
| error     | string  | Error type                        |
| message   | string  | Detailed error message            |
| path      | string  | Endpoint path                     |

### Example

```json
{
  "timestamp": "2024-06-01T12:34:56",
  "status": 404,
  "error": "Not Found",
  "message": "Resource not found",
  "path": "/api/v1/resource/123"
}
```
