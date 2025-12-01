# Cart Feature

## Overview

Manages user shopping carts, allowing products to be added, removed, and viewed.

## Endpoints

| Method | Endpoint                              | Description                | Auth Required |
|--------|---------------------------------------|----------------------------|--------------|
| GET    | /api/v1/cart/{userId}                 | Get user's cart            | Yes          |
| POST   | /api/v1/cart/{cartId}/add             | Add product to cart        | Yes          |
| DELETE | /api/v1/cart/{cartId}/remove/{productId} | Remove product from cart | Yes          |

## Usage Flow

1. Retrieve the cart for a user.
2. Add products to the cart specifying quantity.
3. Remove products from the cart.

### Example: Add Product to Cart

```http
POST /api/v1/cart/1/add?quantity=2
Authorization: Bearer <token>
Content-Type: application/json

{
  "id": 10,
  "name": "Product Name",
  "price": 19.99
}
```

### Example: Remove Product from Cart

```http
DELETE /api/v1/cart/1/remove/10
Authorization: Bearer <token>
```
