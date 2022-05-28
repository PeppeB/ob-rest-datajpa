package com.example.obrestdatajpa;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repository.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class ObRestDatajpaApplication {

	public static void main(String[] args) {
		//  creiamo un application context, ossia un contenitore di beans.
		ApplicationContext context = SpringApplication.run(ObRestDatajpaApplication.class, args);
		BookRepository repository = context.getBean(BookRepository.class); // rappresenta il database

		// Il metodo findAll() dell'interfaccia BookRepository (ereditato dalla super interfaccia JpaRepository),
		// restituisce una lista di oggetti della classe Book (guardare la definizione di BookRepository). Il metodo
		// size() restituisce la dimensione di questa lista.
		System.out.println("Num libros en base de datos: " + repository.findAll().size());

		// CRUD

		// crear un libro
		Book book1 = new Book(null, "Homo Deus", "Yuval Noah", 450, 29.99, LocalDate.of(2018,12,1), true);
		Book book2 = new Book(null, "Homo Sapiens", "Yuval Noah", 450, 19.99, LocalDate.of(2013,12,1), true);
		// almacenar un libro
		repository.save(book1);
		repository.save(book2);
		// recuperar todos los libros
		System.out.println("Num libros en base de datos: " + repository.findAll().size());
		// borrar un libro
		// repository.deleteById(1L); // cancellare il libro con id=1 (L sta per Long)
		System.out.println("Num libros en base de datos: " + repository.findAll().size());
	}

}
