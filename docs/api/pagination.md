# Pagination

Pagination is supported via query parameters.

| Parameter | Type    | Description                       |
|-----------|---------|-----------------------------------|
| page      | integer | Page number (starting from 0)     |
| size      | integer | Number of items per page          |
| sort      | string  | Sort field (e.g., `name,asc`)     |

### Example Request

```
GET /api/v1/resource?page=0&size=10&sort=name,asc
```

### Example Paginated Response

```json
{
  "content": [
    { "id": 1, "name": "A" },
    { "id": 2, "name": "B" }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 50,
  "totalPages": 5
}
```
