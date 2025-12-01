# REST API Structure

## Versioning

- All endpoints are prefixed with `/api/v1/`.
- Future versions will use `/api/v2/`, etc.

## URL Conventions

- Resource-based URLs (e.g., `/products/list`, `/cart/{userId}`)
- Actions as sub-paths (e.g., `/cart/{cartId}/add`)

## Payload Conventions

- Requests and responses use JSON.
- Error responses follow a standard structure with `timestamp`, `status`, `error`, `message`, and `path`.

## Example

```http
POST /api/v1/products
Content-Type: application/json

{
  "name": "Example Product",
  "price": 10.99
}
```
