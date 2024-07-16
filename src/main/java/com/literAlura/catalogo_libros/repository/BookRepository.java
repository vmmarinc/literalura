package com.literAlura.catalogo_libros.repository;

import com.literAlura.catalogo_libros.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
   // List<Book> findByLanguage(String language);
   @Query("SELECT b FROM Book b JOIN FETCH b.authors")
   List<Book> findAllBooksWithAuthors();
}
