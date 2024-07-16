package com.literAlura.catalogo_libros.service;

import com.literAlura.catalogo_libros.model.ApiResponse;
import com.literAlura.catalogo_libros.model.Author;
import com.literAlura.catalogo_libros.model.Book;
import com.literAlura.catalogo_libros.repository.AuthorRepository;
import com.literAlura.catalogo_libros.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final RestTemplate restTemplate;
    private final List<Book> books = new ArrayList<>();

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, RestTemplate restTemplate) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.restTemplate = restTemplate;
    }

    public void saveBook(Book book) {
        Author author = book.getAuthors().get(0);
        authorRepository.save(author); // Guardar el autor primero
        bookRepository.save(book); // Luego guardar el libro
    }

    public Book searchBookByTitle(String title) {
        String url = "https://gutendex.com/books/?search=" + title;
        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
        if (response != null && !response.getResults().isEmpty()) {
            Book book = response.getResults().get(0);
            books.add(book);
            saveBook(book); // Guarda el libro en la base de datos
            return book;
        }
        return null;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAllBooksWithAuthors();
    }

    public List<Author> getAllAuthors() {
        return books.stream()
                .map(book -> book.getAuthors().get(0))
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Author> getLivingAuthors(int year) {
        return books.stream()
                .map(book -> book.getAuthors().get(0))
                .filter(author -> author.getBirthYear() <= year && (author.getDeathYear() == 0 || author.getDeathYear() >= year))
                .distinct()
                .collect(Collectors.toList());
    }

    // Método para obtener todos los libros de la API
    public List<Book> fetchAllBooks() {
        List<Book> allBooks = new ArrayList<>();
        int page = 1;
        boolean hasMoreBooks = true;

        while (hasMoreBooks) {
            String url = "https://gutendex.com/books/?page=" + page;
            ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
            if (response != null && !response.getResults().isEmpty()) {
                allBooks.addAll(response.getResults());
                page++;
            } else {
                hasMoreBooks = false;
            }
        }

        return allBooks;
    }

    // Método para contar libros por idioma
    public Map<String, Long> countBooksByLanguage() {
        List<Book> allBooks = fetchAllBooks();
        return allBooks.stream()
                .flatMap(book -> book.getLanguages().stream())
                .collect(Collectors.groupingBy(lang -> lang, Collectors.counting()));
    }
}


//package com.literAlura.catalogo_libros.service;
//
//import com.literAlura.catalogo_libros.model.ApiResponse;
//import com.literAlura.catalogo_libros.model.Author;
//import com.literAlura.catalogo_libros.model.Book;
//import com.literAlura.catalogo_libros.repository.AuthorRepository;
//import com.literAlura.catalogo_libros.repository.BookRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class BookService {
//    private final BookRepository bookRepository;
//    private final AuthorRepository authorRepository;
//    private final RestTemplate restTemplate;
//    private final List<Book> books = new ArrayList<>();
//
//    @Autowired
//    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, RestTemplate restTemplate) {
//        this.bookRepository = bookRepository;
//        this.authorRepository = authorRepository;
//        this.restTemplate = restTemplate;
//    }
//
//    public void saveBook(Book book) {
//        Author author = book.getAuthors().get(0);
//        authorRepository.save(author); // Guardar el autor primero
//        bookRepository.save(book); // Luego guardar el libro
//    }
//
//    public Book searchBookByTitle(String title) {
//        String url = "https://gutendex.com/books/?search=" + title;
//        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
//        if (response != null && !response.getResults().isEmpty()) {
//            Book book = response.getResults().get(0);
//            books.add(book);
//            saveBook(book); // Guarda el libro en la base de datos
//            return book;
//        }
//        return null;
//    }
//
//    public List<Book> getAllBooks() {
//        return bookRepository.findAllBooksWithAuthors();
//    }
//
//    public List<Author> getAllAuthors() {
//        return books.stream()
//                .map(book -> book.getAuthors().get(0))
//                .distinct()
//                .collect(Collectors.toList());
//    }
//
//    public List<Author> getLivingAuthors(int year) {
//        return books.stream()
//                .map(book -> book.getAuthors().get(0))
//                .filter(author -> author.getBirthYear() <= year && (author.getDeathYear() == 0 || author.getDeathYear() >= year))
//                .distinct()
//                .collect(Collectors.toList());
//    }
//}
//


//package com.literAlura.catalogo_libros.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.literAlura.catalogo_libros.model.ApiResponse;
//import com.literAlura.catalogo_libros.model.Author;
//import com.literAlura.catalogo_libros.model.Book;
//import com.literAlura.catalogo_libros.repository.AuthorRepository;
//import com.literAlura.catalogo_libros.repository.BookRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class BookService {
//    private final BookRepository bookRepository;
//    private final AuthorRepository authorRepository;
//    private final RestTemplate restTemplate;
//    private final ObjectMapper objectMapper;
//    private final List<Book> books = new ArrayList<>();
//
//    @Autowired
//    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
//        this.bookRepository = bookRepository;
//        this.authorRepository = authorRepository;
//        this.restTemplate = restTemplate;
//        this.objectMapper = objectMapper;
//    }
//
//    public void saveBook(Book book) {
//        Author author = book.getAuthors().get(0);
//        authorRepository.save(author); // Guardar el autor primero
//        bookRepository.save(book); // Luego guardar el libro
//    }
//
//    public Book searchBookByTitle(String title) {
//        String url = "https://gutendex.com/books/?search=" + title;
//        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
//        if (response != null && !response.getResults().isEmpty()) {
//            Book book = response.getResults().get(0);
//            books.add(book);
//            saveBook(book); // Guarda el libro en la base de datos
//            return book;
//        }
//        return null;
//    }
//
//    public List<Book> getAllBooks() {
//        return bookRepository.findAll();
//    }
//
//    public List<Author> getAllAuthors() {
//        return books.stream()
//                .map(book ->  book.getAuthors().get(0))
//                .distinct()
//                .collect(Collectors.toList());
//    }
//
//    public List<Author> getLivingAuthors(int year) {
//        return books.stream()
//                .map(book ->  book.getAuthors().get(0))
//                .filter(author -> author.getBirthYear() <= year && (author.getDeathYear() == 0 || author.getDeathYear() >= year))
//                .distinct()
//                .collect(Collectors.toList());
//    }
//}
//
//
//
//
//
////package com.literAlura.catalogo_libros.service;
////
////import com.fasterxml.jackson.core.type.TypeReference;
////import com.fasterxml.jackson.databind.ObjectMapper;
////import com.literAlura.catalogo_libros.model.ApiResponse;
////import com.literAlura.catalogo_libros.model.Author;
////import com.literAlura.catalogo_libros.model.Book;
//////import com.literAlura.catalogo_libros.repository.*
////import com.literAlura.catalogo_libros.repository.AuthorRepository;
////import com.literAlura.catalogo_libros.repository.BookRepository;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.context.annotation.Bean;
////import org.springframework.core.ParameterizedTypeReference;
////import org.springframework.http.HttpMethod;
////import org.springframework.stereotype.Service;
////import org.springframework.web.client.RestTemplate;
////import java.util.ArrayList;
////import java.util.List;
////import java.util.stream.Collectors;
////
////@Service
////public class BookService {
////    private final BookRepository bookRepository;
////    private final AuthorRepository authorRepository;
////
////   private final RestTemplate restTemplate;
////   private final List<Book> books = new ArrayList<>();
////
////    @Autowired
////    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
////        this.bookRepository = bookRepository;
////        this.authorRepository = authorRepository;
////    }
////
////    public void saveBook(Book book) {
////        Author author = book.getAuthors();
////        authorRepository.save(author); // Guardar el autor primero
////        bookRepository.save(book); // Luego guardar el libro
////    }
////
////    @Autowired
////   private final ObjectMapper objectMapper;
////
////    private final String BASE_URL = "https://gutendex.com/books/";
////
////    @Autowired
////    public BookService(RestTemplate restTemplate, ObjectMapper objectMapper) {
////        this.restTemplate = restTemplate;
////
////        this.objectMapper = objectMapper;
////    }
////
////    public Book searchBookByTitle(String title) {
////        String url = "https://gutendex.com/books/?search=" + title;
////        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
////        if (response != null && !response.getResults().isEmpty()) {
////            Book book = response.getResults().get(0);
////            books.add(book);
////            return book;
////        }
////        return null;
////    }
////
//////    public Book searchBookByTitle(String title) {
//////        String url = BASE_URL + "?search=" + title;
//////        List<Book> books = restTemplate.exchange(
//////                url,
//////                HttpMethod.GET,
//////                null,
//////                new ParameterizedTypeReference<List<Book>>() {}).getBody();
//////
//////        return books != null && !books.isEmpty() ? books.get(0) : null;
//////    }
////
////    public List<Book> getAllBooks() {
////        // Aquí podrías mantener un registro de los libros buscados
////        return bookRepository.findAll();
////        //return new ArrayList<>(books);
////    }
////
////    public List<Author> getAllAuthors() {
////        return books.stream()
////                .map(book ->  book.getAuthors().get(0))
////                .distinct()
////                .collect(Collectors.toList());
////    }
////
////    public List<Author> getLivingAuthors(int year) {
////        return books.stream()
////                .map(book ->  book.getAuthors().get(0))
////                .filter(author -> author.getBirthYear() <= year && (author.getDeathYear() == 0 || author.getDeathYear() >= year))
////                .distinct()
////                .collect(Collectors.toList());
////    }
////}
