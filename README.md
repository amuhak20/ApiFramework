# REST Assured Sample Framework

A small, reusable Java 8+ API test framework built with Maven, REST Assured,
JUnit 5, and WireMock. Its example tests run against an in-process mock server,
so no external API or credentials are required.

## Requirements

- JDK 8 or newer
- Maven 3.6 or newer

## Run the tests

```shell
mvn test
```

## Project layout

```text
src/test/java/com/example/api/
  client/       API client and endpoint methods
  config/       Environment and timeout configuration
  model/        Request/response models
  support/      Shared request/response specifications and test setup
  tests/        Example API tests
src/test/resources/
  api.properties
```

## Configuration

Defaults are in `src/test/resources/api.properties`. The API base URI can be
overridden with the `api.baseUri` Java system property or `API_BASE_URI`
environment variable. The request timeout can be overridden with the
`api.timeoutMillis` system property or `API_TIMEOUT_MILLIS` environment
variable.

The sample test class starts WireMock on a free local port and injects that
URI into the API client. For real API tests, use the same `ApiConfig` and
`ApiClient` with your target API and add environment-specific test data and
credentials outside source control.
