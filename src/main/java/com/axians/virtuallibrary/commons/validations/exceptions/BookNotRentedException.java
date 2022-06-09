package com.axians.virtuallibrary.commons.validations.exceptions;

import org.springframework.http.HttpStatus;

public class BookNotRentedException extends GenericResourceException{

	private static final long serialVersionUID = 1L;

	public BookNotRentedException() {
		super(HttpStatus.BAD_REQUEST, "This book not rented for this user!");
	}
}
