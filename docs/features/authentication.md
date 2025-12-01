# Authentication

## Login Flow

1. User sends a POST request to `/api/v1/auth/login` with `username` and `password`.
2. Credentials are validated against the database.
3. If valid, a JWT token is generated and returned.

### Example Request

```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "user1",
  "password": "password123"
}
```

### Example Response

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
}
```

## Tokens

- JWT tokens are used for stateless authentication.
- Include the token in the `Authorization` header as `Bearer <token>` for protected endpoints.

## Endpoint Protection

- Public endpoints: `/api/v1/auth/**`, `/api/v1/test-captcha/**`
- All other endpoints require a valid JWT token.

### Example Protected Request

```http
GET /api/v1/products/list
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...
```

## Registration

- Users can register via `POST /api/v1/auth/register` with `username` and `password`.
- Passwords are securely hashed before storage.

### Example Registration Request

```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "username": "newuser",
  "password": "newpassword"
}
```
