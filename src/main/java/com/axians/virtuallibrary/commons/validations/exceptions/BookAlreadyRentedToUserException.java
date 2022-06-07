package com.axians.virtuallibrary.commons.validations.exceptions;

import org.springframework.http.HttpStatus;

public class BookAlreadyRentedToUserException extends GenericResourceException{

	private static final long serialVersionUID = 1L;

	public BookAlreadyRentedToUserException() {
		super(HttpStatus.CONFLICT, "Operation not completed, because this book is already rented to this user!");
	}
}
