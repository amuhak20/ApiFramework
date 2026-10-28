package com.example.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ApiConfig {
    private static final String CONFIG_FILE = "api.properties";

    private final String baseUri;
    private final int timeoutMillis;

    private ApiConfig(String baseUri, int timeoutMillis) {
        this.baseUri = baseUri;
        this.timeoutMillis = timeoutMillis;
    }

    public static ApiConfig load() {
        Properties properties = new Properties();
        try (InputStream input = ApiConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IllegalStateException("Missing configuration resource: " + CONFIG_FILE);
            }
            properties.load(input);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load " + CONFIG_FILE, exception);
        }

        String baseUri = setting("api.baseUri", "API_BASE_URI", properties, "api.baseUri");
        String timeout = setting("api.timeoutMillis", "API_TIMEOUT_MILLIS", properties, "api.timeoutMillis");
        if (baseUri == null || baseUri.trim().isEmpty()) {
            throw new IllegalStateException("API base URI must not be empty");
        }

        try {
            int timeoutMillis = Integer.parseInt(timeout);
            if (timeoutMillis <= 0) {
                throw new IllegalArgumentException("API timeout must be greater than zero");
            }
            return new ApiConfig(baseUri, timeoutMillis);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("API timeout must be a positive integer: " + timeout, exception);
        }
    }

    private static String setting(String propertyName, String environmentName,
                                  Properties properties, String configKey) {
        String value = System.getProperty(propertyName);
        if (value == null || value.trim().isEmpty()) {
            value = System.getenv(environmentName);
        }
        return value == null || value.trim().isEmpty() ? properties.getProperty(configKey) : value;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public int getTimeoutMillis() {
        return timeoutMillis;
    }

    public ApiConfig withBaseUri(String newBaseUri) {
        if (newBaseUri == null || newBaseUri.trim().isEmpty()) {
            throw new IllegalArgumentException("API base URI must not be empty");
        }
        return new ApiConfig(newBaseUri, timeoutMillis);
    }
}
