# Catalogo de Libros

Este proyecto es un sistema de gestión de libros que permite buscar, guardar y listar libros junto con sus autores utilizando la API de Gutendex. Además, proporciona estadísticas sobre los libros almacenados en la base de datos.

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal.
- **Spring Boot**: Framework para crear aplicaciones Java basadas en microservicios.
- **Hibernate**: ORM para interactuar con la base de datos.
- **PostgreSQL**: Sistema de gestión de bases de datos.
- **RestTemplate**: Para realizar llamadas a la API de Gutendex.

## Funcionalidades

1. **Buscar libros por título**: Permite buscar un libro a través de su título utilizando la API de Gutendex y guardar los resultados en la base de datos.
2. **Listar libros registrados**: Muestra todos los libros que han sido guardados en la base de datos.
3. **Listar autores registrados**: Muestra todos los autores de los libros guardados.
4. **Listar autores vivos**: Filtra y muestra los autores que están vivos en un año determinado.
5. **Listar libros por idioma**: Muestra todos los libros organizados por su idioma.
6. **Contar libros por idioma**: Ofrece estadísticas sobre la cantidad de libros disponibles en la base de datos por idioma.

## Requisitos

- Java 17 o superior
- PostgreSQL
- Maven

## Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu_usuario/catalogo_libros.git
   cd catalogo_libros

