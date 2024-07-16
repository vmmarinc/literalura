package com.literAlura.catalogo_libros.service;

import java.net.http.HttpClient;

public class ApiClient {
    private final HttpClient httpClient;

    public ApiClient() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }
}
