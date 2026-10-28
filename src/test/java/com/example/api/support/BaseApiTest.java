package com.example.api.support;

import com.example.api.client.PostsApi;
import com.example.api.config.ApiConfig;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public abstract class BaseApiTest {
    protected WireMockServer mockServer;
    protected PostsApi postsApi;

    @BeforeEach
    void startMockServer() {
        mockServer = new WireMockServer(options().dynamicPort());
        mockServer.start();
        ApiConfig config = ApiConfig.load().withBaseUri(mockServer.baseUrl());
        postsApi = new PostsApi(Specifications.request(config));
    }

    @AfterEach
    void stopMockServer() {
        if (mockServer != null) {
            mockServer.stop();
        }
    }
}
