package com.example.api.tests;

import com.example.api.model.Post;
import com.example.api.support.BaseApiTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PostsApiTest extends BaseApiTest {
    @Test
    void getsPostById() {
        mockServer.stubFor(get(urlEqualTo("/posts/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"userId\":1,\"id\":1,\"title\":\"Example title\",\"body\":\"Example body\"}")));

        Response response = postsApi.getPost(1);

        response.then()
                .spec(com.example.api.support.Specifications.successfulResponse())
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", equalTo("Example title"));
    }

    @Test
    void createsPostFromModel() {
        mockServer.stubFor(post(urlEqualTo("/posts"))
                .withRequestBody(equalToJson(
                        "{\"userId\":7,\"id\":0,\"title\":\"New title\",\"body\":\"New body\"}"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"userId\":7,\"id\":101,\"title\":\"New title\",\"body\":\"New body\"}")));

        Response response = postsApi.createPost(new Post(7, "New title", "New body"));

        response.then()
                .spec(com.example.api.support.Specifications.successfulResponse())
                .statusCode(201)
                .body("userId", equalTo(7))
                .body("id", notNullValue());
        assertEquals(101, response.jsonPath().getInt("id"));
        assertThat(response.jsonPath().getString("title"), is(equalTo("New title")));
    }
}
