# Metrics

## Overview

Application exposes metrics via Spring Boot Actuator.

## Common Metrics

- JVM memory usage
- HTTP request count and latency
- Active threads
- Custom business metrics (if implemented)

## Accessing Metrics

- Endpoint: `/actuator/metrics`
- Example: `/actuator/metrics/jvm.memory.used`

## Integration

- Integrate with Prometheus for scraping metrics.
- Visualize with Grafana or similar tools.
