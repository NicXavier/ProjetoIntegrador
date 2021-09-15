package com.Projeto.Integrador.Grupo4.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Projeto.Integrador.Grupo4.Model.BookModel;
import com.Projeto.Integrador.Grupo4.Repository.BookRepository;
import com.Projeto.Integrador.Grupo4.service.BookService;
import com.Projeto.Integrador.Grupo4.service.exception.DataIntegratyViolationException;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin("*")
@RequestMapping("/book")
@Api(tags = "Controlador de BOOK", description = "Utilitario de Postagens")
public class BookController {

	@Autowired
	private BookRepository repository;

	@Autowired
	private BookService service;

	@GetMapping("/all")
	public ResponseEntity<List<BookModel>> getAll() {
		List<BookModel> obj = repository.findAll();
		if (obj.isEmpty()) {
			throw new DataIntegratyViolationException("Não existe nenhum livro cadastrado");
		} else {
			return ResponseEntity.status(200).body(obj);
		}
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<BookModel> findById(@PathVariable Long id) {
		Optional<BookModel> idObj = repository.findById(id);
		if (idObj.isPresent()) {
			return ResponseEntity.status(200).body(idObj.get());
		} else {
			throw new DataIntegratyViolationException("Não existe livro com esse id");
		}
	}

	@GetMapping(value = "/title/{title}")
	public ResponseEntity<List<BookModel>> findByDescriptionTitle(@PathVariable String title) {
		ResponseEntity<List<BookModel>> titleObj = service.findByDescriptionTitle(title);
		return titleObj;

	}

	@PutMapping
	public ResponseEntity<BookModel> update(@Valid @RequestBody BookModel updateBook) {
		return ResponseEntity.status(201).body(repository.save(updateBook));
	}

	@DeleteMapping(value = "/{id}")
	public void deleteById(@PathVariable Long id) {
		findById(id);
		repository.deleteById(id);
	}

}
