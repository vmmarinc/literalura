package com.literAlura.catalogo_libros.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literAlura.catalogo_libros.model.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public static List<Book> parseBooks(String jsonResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonResponse);

        
        JsonNode booksNode = rootNode.get("results");
        if (booksNode == null) {
            System.out.println("No se encontró el nodo 'results' en la respuesta JSON.");
            System.out.println("Estructura del JSON: " + rootNode.toPrettyString());
            return new ArrayList<>();
        }

        return mapper.readValue(booksNode.toString(), new TypeReference<List<Book>>() {});
    }
}
