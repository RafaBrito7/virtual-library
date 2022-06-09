package com.axians.virtuallibrary.api.rest;

import java.util.List;

import javax.validation.Valid;

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

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/book")
@CrossOrigin("*")
public class BookRest {
	
	private final BookService bookService;
	
	public BookRest(BookService bookService) {
		this.bookService = bookService;
	}
	
	@PreAuthorize("hasAnyRole('ROOT', 'ADMIN')")
	@PostMapping("/create")
	@ApiOperation("Operation to create a book for system(Only users with permissions: root or admin)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Return a status of created"),
			@ApiResponse(responseCode = "400", description = "Bad Request, some parameter invalid"),
			@ApiResponse(responseCode = "403", description = "Forbidden, not authorized to create"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable")
	})
	public ResponseEntity<?> create(@RequestBody @Valid BookDTO book) {
		this.bookService.create(book);
		return ResponseEntity.ok(new ResponseEntity<>(HttpStatus.CREATED));
	}
	
	@PreAuthorize("hasAnyRole('ROOT', 'ADMIN', 'USER', 'VIEWER')")
	@GetMapping("/list")
	@ApiOperation("Operation to list all books in the system(any permission)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Return a list of all books in the system"),
			@ApiResponse(responseCode = "204", description = "Return a empty list if no have book in database"),
			@ApiResponse(responseCode = "403", description = "Forbidden, not authorized to list"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable")
	})
	public ResponseEntity<?> list() {
		List<BookDTO> bookList = this.bookService.list();
		
		if (bookList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(bookList);
	}
	
	@PreAuthorize("hasAnyRole('ROOT', 'ADMIN')")
	@DeleteMapping("/delete/{resourceHyperIdentifier}")
	@ApiOperation("Operation to delete a book(Only users with permissions: root or admin)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Return a status ok if book has deleted"),
			@ApiResponse(responseCode = "403", description = "Forbidden, not authorized to delete"),
			@ApiResponse(responseCode = "404", description = "Book not found in database with this identifier"),
			@ApiResponse(responseCode = "412", description = "A pre-condition failed, book unavailable"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable")
	})
	public ResponseEntity<?> delete(@PathVariable("resourceHyperIdentifier") String resourceHyperIdentifier) {
		this.bookService.delete(resourceHyperIdentifier);
		return ResponseEntity.ok(new ResponseEntity<>(HttpStatus.OK));
	}
	
	@PreAuthorize("hasAnyRole('ROOT', 'ADMIN', 'USER')")
	@PutMapping("/rent/{resourceHyperIdentifier}")
	@ApiOperation("Operation to rent a book(Only users with permissions: root, admin or user)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Return a status ok if book rented"),
			@ApiResponse(responseCode = "403", description = "Forbidden, not authorized to rent"),
			@ApiResponse(responseCode = "404", description = "Book not found in database with this identifier"),
			@ApiResponse(responseCode = "412", description = "A pre-condition failed, book unavailable"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable")
	})
	public ResponseEntity<?> rent(@PathVariable("resourceHyperIdentifier") String resourceHyperIdentifier) {
		this.bookService.rentBook(resourceHyperIdentifier);
		return ResponseEntity.ok(new ResponseEntity<>(HttpStatus.OK));
	}
	
	@PreAuthorize("hasAnyRole('ROOT', 'ADMIN', 'USER')")
	@PutMapping("/refund/{resourceHyperIdentifier}")
	@ApiOperation("Operation to refund a book(Only users with permissions: root, admin or user)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Return a status ok if book refund"),
			@ApiResponse(responseCode = "403", description = "Forbidden, not authorized to refund"),
			@ApiResponse(responseCode = "404", description = "Book not found in database with this identifier"),
			@ApiResponse(responseCode = "412", description = "A pre-condition failed, book unavailable"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable")
	})
	public ResponseEntity<?> refund(@PathVariable("resourceHyperIdentifier") String resourceHyperIdentifier) {
		this.bookService.refundBook(resourceHyperIdentifier);
		return ResponseEntity.ok(new ResponseEntity<>(HttpStatus.OK));
	}

}
