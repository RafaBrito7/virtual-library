package com.axians.virtuallibrary.api.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.axians.virtuallibrary.api.model.dto.BookDTO;
import com.axians.virtuallibrary.api.model.entity.Book;
import com.axians.virtuallibrary.api.model.entity.User;
import com.axians.virtuallibrary.api.repository.BookRepository;
import com.axians.virtuallibrary.api.repository.BookRepositoryCustomImpl;
import com.axians.virtuallibrary.commons.utils.enums.CategoryBookEnum;
import com.axians.virtuallibrary.commons.validations.ValidateStringIsInvalid;
import com.axians.virtuallibrary.commons.validations.exceptions.BookAlreadyRentedToUserException;
import com.axians.virtuallibrary.commons.validations.exceptions.BookNotRentedException;
import com.axians.virtuallibrary.commons.validations.exceptions.DeleteBookRentedException;
import com.axians.virtuallibrary.commons.validations.exceptions.NotFoundResourceException;
import com.axians.virtuallibrary.commons.validations.exceptions.validates.ValidateBookException;
import com.axians.virtuallibrary.commons.validations.exceptions.validates.ValidateBookUnavailableException;

@Service
public class BookService {
	
	private Logger logger = LoggerFactory.getLogger(BookService.class);

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
		logger.info("Starting a book creation operation");
		ValidateBookException.validate(bookDTO);

		Book book = bookDTO.generatePersistObjectToCreate();

		this.bookRepository.save(book);
		logger.info("Book created with success!");
	}
	
	public List<BookDTO> list(){
		logger.info("Starting a book listing operation");
		return this.bookRepositoryCustomImpl.listBooksGrouped();
	}
	
	public List<String> listCategories() {
		logger.info("Starting a category book listing operation");
		return Arrays.asList(CategoryBookEnum.values()).stream()
				.map(CategoryBookEnum::getDescription)
				.collect(Collectors.toList());
	}

	public void delete(String resourceIdentifier) {
		logger.info("Starting a book delete operation");
		ValidateStringIsInvalid.isInvalid(resourceIdentifier);

		getBookByResourceId(resourceIdentifier).ifPresentOrElse(book -> {
			logger.info("Book founded in DataBase, preparing to delete");
			if (Boolean.TRUE.equals(book.getAvailable())) {
				logger.info("The book is available and will be deleted");
				this.bookRepository.delete(book);
			} else {
				logger.error("The book is not available and can't be deleted");
				throw new DeleteBookRentedException();
			}
		}, () -> {
			logger.error("Book not found with identifier: ");
			logger.error(resourceIdentifier);
			throw new NotFoundResourceException();
		});
	}
	
	private Optional<Book> getBookByResourceId(String resourceIdentifier){
		return Optional.ofNullable(this.bookRepository.findByResourceHyperIdentifier(resourceIdentifier));
	}

	public void rentBook(String resourceIdentifier) {
		logger.info("Starting Rent Book Operation");

		User user = this.userService.getLoggedUser();
		getBookByResourceId(resourceIdentifier).ifPresentOrElse(book -> {
			ValidateBookUnavailableException.validate(book);
			
			if (user.getRentedBooks().contains(book)) {
				logger.error("Error! This book already rented to this user!");
				throw new BookAlreadyRentedToUserException();
			}
			
			executeRent(user, book);
		}, () -> {
			logger.error("The book is not found for this identifier!");
			throw new NotFoundResourceException();
		});
	}

	private void executeRent(User user, Book book) {
		user.addRentedBook(book);
		this.userService.update(user);
		book.rent();
		update(book);
		logger.info("Operation of Rent Book completed with success!");
	}
	
	public Book update(Book book) {
		return this.bookRepository.save(book);
	}
	
	public void refundBook(String resourceIdentifier) {
		logger.info("Starting Refund Book Operation");

		User user = this.userService.getLoggedUser();
		getBookByResourceId(resourceIdentifier).ifPresentOrElse(book -> {

			if (user.getRentedBooks().contains(book)) {
				executeRefund(user, book);
			} else {
				logger.error("Error! This book not rented to this user!");
				throw new BookNotRentedException();
			}
		}, () -> {
			logger.error("The book is not found for this identifier!");
			throw new NotFoundResourceException();
		});
	}
	
	private void executeRefund(User user, Book book) {
		user.removeBook(book);
		this.userService.update(user);
		book.refund();
		update(book);
		logger.info("Operation of Refund Book completed with success!");
	}
}
