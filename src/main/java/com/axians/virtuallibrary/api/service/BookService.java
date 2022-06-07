package com.axians.virtuallibrary.api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.axians.virtuallibrary.api.model.dto.BookDTO;
import com.axians.virtuallibrary.api.model.entity.Book;
import com.axians.virtuallibrary.api.model.entity.User;
import com.axians.virtuallibrary.api.repository.BookRepository;
import com.axians.virtuallibrary.api.repository.BookRepositoryCustomImpl;
import com.axians.virtuallibrary.commons.validations.ValidateStringIsInvalid;
import com.axians.virtuallibrary.commons.validations.exceptions.BookAlreadyRentedToUserException;
import com.axians.virtuallibrary.commons.validations.exceptions.DeleteBookRentedException;
import com.axians.virtuallibrary.commons.validations.exceptions.NotFoundResourceException;
import com.axians.virtuallibrary.commons.validations.exceptions.validates.ValidateBookException;
import com.axians.virtuallibrary.commons.validations.exceptions.validates.ValidateBookUnavailableException;

@Service
public class BookService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

	private BookRepository bookRepository;
	
	private BookRepositoryCustomImpl bookRepositoryCustomImpl;
	
	private UserService userService;
	
	public BookService(BookRepository bookRepository, BookRepositoryCustomImpl bookRepositoryCustomImpl,
			UserService userService) {
		this.bookRepository = bookRepository;
		this.bookRepositoryCustomImpl = bookRepositoryCustomImpl;
		this.userService = userService;
	}

	public void create(BookDTO bookDTO) {
		LOGGER.info("Starting a book creation operation");
		ValidateBookException.validate(bookDTO);

		Book book = bookDTO.generatePersistObjectToCreate();

		this.bookRepository.save(book);
		LOGGER.info("Book created with success!");
	}
	
	public List<BookDTO> list(){
		LOGGER.info("Starting a book listing operation");
		return this.bookRepositoryCustomImpl.listBooksGrouped();
	}

	public void delete(String resourceIdentifier) {
		LOGGER.info("Starting a book delete operation");
		ValidateStringIsInvalid.isInvalid(resourceIdentifier);

		getBookByResourceId(resourceIdentifier).ifPresentOrElse(book -> {
			LOGGER.info("Book founded in DataBase, preparing to delete");
			if (book.getAvailable()) {
				LOGGER.info("The book is available and will be deleted");
				this.bookRepository.delete(book);
			} else {
				LOGGER.error("The book is not available and can't be deleted");
				throw new DeleteBookRentedException();
			}
		}, () -> {
			LOGGER.error("Book not found with identifier: " + resourceIdentifier);
			throw new NotFoundResourceException();
		});
	}
	
	private Optional<Book> getBookByResourceId(String resourceIdentifier){
		return Optional.ofNullable(this.bookRepository.findByResourceHyperIdentifier(resourceIdentifier));
	}

	public void rentBook(String resourceIdentifier) {
		LOGGER.info("Starting Rent Book Operation");
		ValidateStringIsInvalid.isInvalid(resourceIdentifier);

		User user = this.userService.getLoggedUser();
		getBookByResourceId(resourceIdentifier).ifPresentOrElse(book -> {
			ValidateBookUnavailableException.validate(book);
			
			if (user.getRentedBooks().contains(book)) {
				LOGGER.error("Error! This book already rented to this user!");
				throw new BookAlreadyRentedToUserException();
			}
			
			executeRent(user, book);
		}, () -> {
			LOGGER.error("The book is not found for this identifier!");
			throw new NotFoundResourceException();
		});
	}

	private void executeRent(User user, Book book) {
		user.addRentedBook(book);
		this.userService.update(user);
		book.rent();
		update(book);
		LOGGER.info("Operation of Rent Book completed with success!");
	}
	
	public Book update(Book book) {
		return this.bookRepository.save(book);
	}
}
