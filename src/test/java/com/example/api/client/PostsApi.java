package com.example.api.client;

import com.example.api.model.Post;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostsApi {
    private final RequestSpecification request;

    public PostsApi(RequestSpecification request) {
        this.request = request;
    }

    public Response getPost(int postId) {
        return given()
                .spec(request)
                .pathParam("postId", postId)
                .when()
                .get("/posts/{postId}");
    }

    public Response createPost(Post post) {
        return given()
                .spec(request)
                .body(post)
                .when()
                .post("/posts");
    }
}
