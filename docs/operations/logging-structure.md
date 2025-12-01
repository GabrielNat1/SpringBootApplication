# Logging Structure

## Log Format

Logs include:
- Timestamp
- Log level
- Logger name
- Message (including IP, method, URI, date, time)

## Example Log

```
[LOGGER] -- [IP: 127.0.0.1] [GET] [/api/v1/products/list] [Date: 2024-06-01] [Time: 12:34:56]
```

## Best Practices

- Use appropriate log levels (`INFO`, `WARN`, `ERROR`).
- Avoid logging sensitive information.
- Centralize logs for production (e.g., ELK Stack, CloudWatch).
