# Monitoring

## Overview

Monitoring ensures the application is running as expected and helps detect issues early.

## Tools

- **Spring Boot Actuator**: Provides endpoints for health, metrics, and more.
- **External Monitoring**: Integrate with tools like Prometheus, Grafana, or ELK Stack for advanced monitoring.

## Recommended Endpoints

- `/actuator/health` - Application health status
- `/actuator/metrics` - Application metrics

## Example: Enable Actuator

Add to `pom.xml`:
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Configure in `application.properties`:
```
management.endpoints.web.exposure.include=health,metrics,info
```
