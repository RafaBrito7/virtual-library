package com.axians.virtuallibrary.api.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
