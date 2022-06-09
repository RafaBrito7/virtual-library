package com.axians.virtuallibrary.api.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axians.virtuallibrary.api.model.dto.BookDTO;
import com.axians.virtuallibrary.api.service.BookService;

@RestController
@RequestMapping("/api/book")
@CrossOrigin("*")
public class BookRest {
	
	private BookService bookService;
	
	public BookRest(BookService bookService) {
		this.bookService = bookService;
	}
	
	@PreAuthorize("hasAnyRole('ROOT', 'ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody BookDTO book) {
		this.bookService.create(book);
		return ResponseEntity.ok(new ResponseEntity<>(HttpStatus.CREATED));
	}
	
	@PreAuthorize("hasAnyRole('ROOT', 'ADMIN', 'USER', 'VIEWER')")
	@GetMapping("/list")
	public ResponseEntity<?> list() {
		List<BookDTO> bookList = this.bookService.list();
		
		if (bookList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(bookList);
	}
	
	@PreAuthorize("hasAnyRole('ROOT', 'ADMIN')")
	@DeleteMapping("/delete/{resourceHyperIdentifier}")
	public ResponseEntity<?> delete(@PathVariable("resourceHyperIdentifier") String resourceHyperIdentifier) {
		this.bookService.delete(resourceHyperIdentifier);
		return ResponseEntity.ok(new ResponseEntity<>(HttpStatus.OK));
	}
	
	@PreAuthorize("hasAnyRole('ROOT', 'ADMIN', 'USER')")
	@PutMapping("/rent/{resourceHyperIdentifier}")
	public ResponseEntity<?> rent(@PathVariable("resourceHyperIdentifier") String resourceHyperIdentifier) {
		this.bookService.rentBook(resourceHyperIdentifier);
		return ResponseEntity.ok(new ResponseEntity<>(HttpStatus.OK));
	}
	
	@PreAuthorize("hasAnyRole('ROOT', 'ADMIN', 'USER')")
	@PutMapping("/refund/{resourceHyperIdentifier}")
	public ResponseEntity<?> refund(@PathVariable("resourceHyperIdentifier") String resourceHyperIdentifier) {
		this.bookService.refundBook(resourceHyperIdentifier);
		return ResponseEntity.ok(new ResponseEntity<>(HttpStatus.OK));
	}

}
