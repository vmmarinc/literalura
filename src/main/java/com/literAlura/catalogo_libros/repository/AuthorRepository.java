package com.literAlura.catalogo_libros.repository;

import com.literAlura.catalogo_libros.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
