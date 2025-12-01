# Troubleshooting

## Common Issues

### Application Fails to Start

- Check logs for stack traces.
- Ensure all environment variables are set.
- Verify database connectivity.

### Authentication Errors

- Confirm JWT secret is set and matches between services.
- Check token expiration.

### Rate Limiting

- If receiving HTTP 429, reduce request frequency.

### Captcha Issues

- Ensure session is enabled and not expiring too quickly.

## Getting More Help

- Review logs for detailed error messages.
- Use `/actuator/health` and `/actuator/metrics` for diagnostics.
