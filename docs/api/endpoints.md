# API Endpoints

| Method | Endpoint                          | Description                    | Auth Required |
|--------|-----------------------------------|--------------------------------|--------------|
| POST   | /api/v1/auth/login                | User login                     | No           |
| POST   | /api/v1/auth/register             | User registration              | No           |
| GET    | /api/v1/auth/login                | Get current user info          | Yes          |
| GET    | /api/v1/hello                     | Hello World test               | No           |
| GET    | /api/v1/compression               | Compression test               | No           |
| GET    | /api/v1/testController            | Rate limit test                | No           |
| GET    | /api/v1/products/list             | List all products              | Yes          |
| GET    | /api/v1/products/{id}             | Get product by ID              | Yes          |
| POST   | /api/v1/products                  | Create new product             | Yes          |
| DELETE | /api/v1/products/{id}             | Delete product                 | Yes          |
| GET    | /api/v1/cart/{userId}             | Get user's cart                | Yes          |
| POST   | /api/v1/cart/{cartId}/add         | Add product to cart            | Yes          |
| DELETE | /api/v1/cart/{cartId}/remove/{productId} | Remove product from cart | Yes          |
| GET    | /api/v1/test-captcha              | Get captcha image              | No           |
| POST   | /api/v1/test-captcha              | Validate captcha               | No           |

*Endpoints are grouped by feature. All `/products`, `/cart`, `/test-captcha`, `/hello`, `/compression`, and `/testController` are implemented in the codebase.*
