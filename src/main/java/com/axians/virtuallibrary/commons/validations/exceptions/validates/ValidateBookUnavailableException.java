package com.axians.virtuallibrary.commons.validations.exceptions.validates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axians.virtuallibrary.api.model.entity.Book;
import com.axians.virtuallibrary.commons.validations.exceptions.BookUnavailableException;

public class ValidateBookUnavailableException {
	
	private static Logger logger = LoggerFactory.getLogger(ValidateBookUnavailableException.class);
	
	public static void validate(Book book) {
		logger.info("Starting Validate Book Status Process");
		if (!book.isAvailable() && book.isRented()) {
			logger.error("Book unavailable already rented!");
			throw new BookUnavailableException("The book is unavailable because is already rented");
		}
		if (!book.isAvailable() && book.isDisabled()) {
			logger.error("Book disabled!");
			throw new BookUnavailableException("The book is unavailable because is currently disabled");
		}
		if (!book.isAvailable()) {
			logger.error("Book unavailable! Active the book in database");
			throw new BookUnavailableException("Book currently unavailable in the moment");
		}
		logger.info("Validate complete with success!");
	}

}
