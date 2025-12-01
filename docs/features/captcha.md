# Captcha Feature

## Overview

Protects endpoints from bots and automated requests using image-based captchas.

## Endpoints

| Method | Endpoint                | Description           | Auth Required |
|--------|-------------------------|-----------------------|--------------|
| GET    | /api/v1/test-captcha    | Get captcha image     | No           |
| POST   | /api/v1/test-captcha    | Validate captcha      | No           |

## Usage Flow

1. Request a captcha image.
2. Submit the answer for validation.

### Example: Get Captcha

```http
GET /api/v1/test-captcha
```

### Example: Validate Captcha

```http
POST /api/v1/test-captcha
Content-Type: application/json

{
  "captcha": "userInput"
}
```
