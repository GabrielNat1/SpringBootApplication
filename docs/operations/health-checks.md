# Health Checks

## Overview

Health checks provide information about the application's status.

## Endpoint

- `/actuator/health` - Returns `UP` if the application is healthy.

## Example Response

```json
{
  "status": "UP"
}
```

## Custom Health Indicators

You can add custom health checks for database, cache, or external services.
