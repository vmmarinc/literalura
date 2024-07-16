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
