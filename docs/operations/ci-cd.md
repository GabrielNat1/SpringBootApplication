# CI/CD (Continuous Integration & Continuous Deployment)

## Overview

CI/CD automates building, testing, and deploying your application, ensuring faster and more reliable releases.

## Recommended Tools

- **GitHub Actions** (or GitLab CI, Jenkins, etc.)
- **Maven** for build and test automation
- **Docker** for containerization (optional)
- **Heroku, AWS, Azure, or other cloud providers** for deployment

## Example Workflow

1. **On push or pull request:**
   - Checkout code
   - Set up Java and Maven
   - Run tests (`mvn test`)
   - Build the application (`mvn clean package`)
   - Optionally build/push Docker image

2. **On main branch merge:**
   - Deploy to staging or production environment

## Example GitHub Actions Workflow

```yaml
name: Java CI

on:
  push:
    branches: [ "main" ]
  pull_request:
    branches: [ "main" ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Build with Maven
        run: mvn clean package
      - name: Run tests
        run: mvn test
```

## Best Practices

- Run tests on every commit.
- Use secrets for sensitive data (e.g., database credentials, JWT secret).
- Automate deployment to reduce manual errors.
- Monitor deployments and roll back on failure.
