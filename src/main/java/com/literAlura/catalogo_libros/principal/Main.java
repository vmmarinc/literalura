package com.literAlura.catalogo_libros.principal;
import com.literAlura.catalogo_libros.model.Author;
import com.literAlura.catalogo_libros.model.Book;
import com.literAlura.catalogo_libros.service.ConsumoAPI;
import com.literAlura.catalogo_libros.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import com.literAlura.catalogo_libros.service.BookService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication(scanBasePackages = "com.literAlura.catalogo_libros")
public class Main implements CommandLineRunner {

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        ConsumoAPI consumoAPI = new ConsumoAPI();
        String url = "https://gutendex.com/books";
        String jsonResponse = consumoAPI.obtenerDatos(url);

        //System.out.println("Respuesta JSON: " + jsonResponse);

        try {
            List<Book> books = JsonParser.parseBooks(jsonResponse);
//            books.forEach(book -> {
//                System.out.println("Title: " + book.getTitle());
//                book.getAuthors().forEach(author -> System.out.println("Author: " + author.getName() + ", Birth Year: " + author.getBirthYear() + ", Death Year: " + author.getDeathYear()));
//            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        mostrarMenu();
    }

    private void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("Elija la opción a través de su número:");
            System.out.println("1- Buscar libro por título");
            System.out.println("2- Listar libros registrados");
            System.out.println("3- Listar autores registrados");
            System.out.println("4- Listar autores vivos en un determinado año");
            System.out.println("5- Listar libros por idioma");
            System.out.println("0- Salir");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida, por favor ingrese un número.");
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.println("Opción 1 seleccionada: Buscar libro por título.");
                    String title = scanner.nextLine();
                    Book book = bookService.searchBookByTitle(title);
                    System.out.println(book != null ? book : "Libro no encontrado.");
                   break;
                case 2:
                    System.out.println("Opción 2 seleccionada: Listar libros registrados.");
                    bookService.getAllBooks().forEach(System.out::println);
//                    List<Book> books = bookService.getAllBooks();
//                    if (books.isEmpty()) {
//                        System.out.println("No hay libros registrados.");
//                    } else {
//                        books.forEach(System.out::println);
//                    }
                    break;
                case 3:
                    System.out.println("Opción 3 seleccionada: Listar autores registrados.");
                    List<Author> authors = bookService.getAllAuthors();
                    System.out.println("Autores registrados:");
                    for (Author author : authors) {
                        System.out.println(author);
                    }
                    break;
                case 4:
                    System.out.println("Opción 4 seleccionada: Listar autores vivos en un determinado año.");
                    System.out.print("Ingrese el año: ");
                    int year = scanner.nextInt();
                    List<Author> livingAuthors = bookService.getLivingAuthors(year);
                    System.out.println("Autores vivos en el año " + year + ":");
                    for (Author author : livingAuthors) {
                        System.out.println(author);
                    }
                    break;
                case 5:
                    System.out.println("Opción 5 seleccionada: Listar libros por idioma.");
                    Map<String, Long> languageCounts = bookService.countBooksByLanguage();
                    languageCounts.forEach((language, count) -> System.out.println(language + ": " + count));
                    break;
                case 0:
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    System.out.println("Opción inválida, por favor intente nuevamente.");
                    break;
            }

        } while (opcion != 0);

        scanner.close();
    }


}
