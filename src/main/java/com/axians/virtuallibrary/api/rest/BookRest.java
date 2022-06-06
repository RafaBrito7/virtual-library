package com.axians.virtuallibrary.api.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axians.virtuallibrary.api.model.dto.BookDTO;
import com.axians.virtuallibrary.api.service.BookService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/book")
@Tag(name = "Book", description = "Book Operations")
@CrossOrigin("*")
public class BookRest {
	
	private BookService bookService;
	
	public BookRest(BookService bookService) {
		this.bookService = bookService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody BookDTO book) {
		this.bookService.create(book);
		return ResponseEntity.ok(new ResponseEntity<>(HttpStatus.CREATED));
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> list() {
		List<BookDTO> bookList = this.bookService.list();
		
		if (bookList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(bookList);
	}

}
