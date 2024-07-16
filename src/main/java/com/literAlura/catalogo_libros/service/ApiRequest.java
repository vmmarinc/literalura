package com.literAlura.catalogo_libros.service;

import java.net.URI;
import java.net.http.HttpRequest;

public class ApiRequest {
    public HttpRequest createRequest(String url) {
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .build();
    }
}
