package com.example.api.support;

import com.example.api.config.ApiConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThan;

public final class Specifications {
    private Specifications() {
    }

    public static RequestSpecification request(ApiConfig config) {
        return new RequestSpecBuilder()
                .setBaseUri(config.getBaseUri())
                .setContentType("application/json")
                .setAccept("application/json")
                .setConfig(io.restassured.config.RestAssuredConfig.config()
                        .httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", config.getTimeoutMillis())
                                .setParam("http.socket.timeout", config.getTimeoutMillis())))
                .build();
    }

    public static ResponseSpecification successfulResponse() {
        return new ResponseSpecBuilder()
                .expectResponseTime(lessThan(5000L))
                .build();
    }
}
