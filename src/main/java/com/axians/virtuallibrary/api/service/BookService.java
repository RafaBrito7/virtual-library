package com.axians.virtuallibrary.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.axians.virtuallibrary.api.model.dto.BookDTO;
import com.axians.virtuallibrary.api.model.entity.Book;
import com.axians.virtuallibrary.api.repository.BookRepository;
import com.axians.virtuallibrary.api.repository.BookRepositoryCustomImpl;
import com.axians.virtuallibrary.commons.validations.exceptions.validates.ValidateBookException;

@Service
public class BookService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

	private BookRepository bookRepository;
	
	private BookRepositoryCustomImpl bookRepositoryCustomImpl;
	
	public BookService(BookRepository bookRepository, BookRepositoryCustomImpl bookRepositoryCustomImpl) {
		this.bookRepository = bookRepository;
		this.bookRepositoryCustomImpl = bookRepositoryCustomImpl;
	}

	public void create(BookDTO bookDTO) {
		LOGGER.info("Starting a book creation operation");
		ValidateBookException.validate(bookDTO);

		Book book = bookDTO.generatePersistObjectToCreate();

		this.bookRepository.save(book);
		LOGGER.info("Book created with success!");
	}
	
	public List<BookDTO> list(){
		return this.bookRepositoryCustomImpl.listBooksGrouped();
	}
	
//	private Optional<List<Book>> listByNameAndCategory(String title, String category) {
//		LOGGER.info("Searching for Books in DataBase");
//		List<Book> books = new ArrayList<>();
//		books = this.bookRepository.findByTitleAndCategory(title, category);
//		return Optional.ofNullable(books);
//	}
}
