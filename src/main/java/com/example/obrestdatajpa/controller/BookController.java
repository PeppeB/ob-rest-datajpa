package com.example.obrestdatajpa.controller;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repository.BookRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

// La classe BookController é un componente che si occupa di realizzare le operazioni CRUD
// sull'entità Book
@RestController
public class BookController {

    // atributos

    private final Logger log = LoggerFactory.getLogger(BookController.class);

    private BookRepository bookRepository;

    // constructores

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // getter y setter


    /* CRUD sobre la entidad Book */

    // Buscar todos los libros que hay en base de datos (ArrayList de libros)

    /**
     * http://localhost:8081/api/books
     * @return list of books
     */
    @GetMapping("/api/books")
    public List<Book> findAll() {
        // recuperar y devolver los libros de base de datos
        return bookRepository.findAll();
    }

    // Buscar un solo libro en base de datos segun su id : solución 1

    //@GetMapping("/api/books/{id}")
    //public Book findOneById2(@PathVariable Long id) {
        // Optional<Book> bookOpt = bookRepository.findById(id);

        // opcion 1

        //if(bookOpt.isPresent()) {
            //return bookOpt.get();
        //} else {
            //return null;
        //}


        // opcion 2
        // return bookOpt.orElse(null);
    //}

    // Buscar un solo libro en base de datos segun su id : solución 2

    /**
     * http://localhost:8081/api/books/1
     * http://localhost:8081/api/books/2
     * Request
     * Response
     * @param id
     * @return Response
     */
    @GetMapping("/api/books/{id}")
    @ApiOperation("Buscar un libro por clave primaria id Long")
    public ResponseEntity<Book> findOneById(@ApiParam("Clave primaria tipo Long") @PathVariable Long id) {
        Optional<Book> bookOpt = bookRepository.findById(id);

        // opcion 1

        if(bookOpt.isPresent()) {
            return ResponseEntity.ok(bookOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }

        // opcion 2
        // return bookOpt.orElse(null);
    }


    /**
     * Crear un nuevo libro en base de datos
     * Metodo POST, no colisiona con findAll() porque son diferentes métodos HTTP: GET vs. POST
     * @param book
     * @param headers
     * @return the book (entity) saved into database
     */
    @PostMapping("/api/books")
    public ResponseEntity<Book> create(@RequestBody Book book, @RequestHeader HttpHeaders headers) {
        System.out.println(headers.get("User-Agent"));
        // guardar libro recibido por parametro en la base de datos
        if(book.getId() != null) { // quire decir que existe el id y por tanto no es una creacíon
            log.warn("Trying to create a book with id");
            System.out.println("Trying to create a book with id");
            return ResponseEntity.badRequest().build();
        }

        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result); // el libro devuelto tiene una clave primaria
    }


    /**
     * Actualizar un libro existente en base de datos
     */
    @PutMapping("/api/books")
    public ResponseEntity<Book> update(@RequestBody Book book) {
        if(book.getId() == null) { // si no tiene id (quiere decir que si es una creacíon)
            log.warn("Trying to update a non existent book");
            return ResponseEntity.badRequest().build();
        }

        if(!bookRepository.existsById(book.getId())) { // si el libro no esiste
            log.warn("Trying to update a non existent book");
            return ResponseEntity.notFound().build();
        }

        // proceso de actualización
        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result); // el libro devuelto tiene una clave primaria
    }


    // Borrar un libro en base de datos

    @ApiIgnore
    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id) {

        if(!bookRepository.existsById(id)) { // si el libro no esiste
            log.warn("Trying to delete a non existent book");
            return ResponseEntity.notFound().build();
        }

        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    // Borrar todos los libros en base de datos

    @ApiIgnore // ignorar este metodo para que no aparezca en la documentación de la api Swagger
    @DeleteMapping("/api/books")
    public ResponseEntity<Book> deleteAll() {
        log.info("REST Request for deleting all books");
        bookRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
