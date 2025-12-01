# Products Feature

## Overview

Manages product catalog, including listing, retrieving, creating, and deleting products.

## Endpoints

| Method | Endpoint                  | Description            | Auth Required |
|--------|---------------------------|------------------------|--------------|
| GET    | /api/v1/products/list     | List all products      | Yes          |
| GET    | /api/v1/products/{id}     | Get product by ID      | Yes          |
| POST   | /api/v1/products          | Create new product     | Yes          |
| DELETE | /api/v1/products/{id}     | Delete product         | Yes          |

## Usage Flow

1. List all products.
2. Retrieve details for a specific product.
3. Create new products.
4. Delete products.

### Example: List Products

```http
GET /api/v1/products/list
Authorization: Bearer <token>
```

### Example: Create Product

```http
POST /api/v1/products
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "New Product",
  "price": 29.99
}
```
