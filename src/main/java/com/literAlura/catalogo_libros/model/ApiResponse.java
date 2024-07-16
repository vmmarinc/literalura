package com.literAlura.catalogo_libros.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {

    @JsonProperty("results")
    private List<Book> results;

    public List<Book> getResults() {
        return results;
    }

    public void setResults(List<Book> results) {
        this.results = results;
    }
}




//package com.literAlura.catalogo_libros.repository;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.literAlura.catalogo_libros.model.Book;
//
//import java.io.IOException;
//import java.util.List;
//
//public class ApiResponse {
//    public List<Book> parseResponse(String jsonResponse) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.readValue(jsonResponse, new TypeReference<List<Book>>() {});
//    }
//}
