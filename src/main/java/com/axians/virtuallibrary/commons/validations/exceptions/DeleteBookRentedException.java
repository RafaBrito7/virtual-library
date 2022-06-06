package com.axians.virtuallibrary.commons.validations.exceptions;

import org.springframework.http.HttpStatus;

public class DeleteBookRentedException extends GenericResourceException {

	private static final long serialVersionUID = 1L;

	public DeleteBookRentedException() {
		super(HttpStatus.PRECONDITION_FAILED, "Unable to remove book as it is rented!");
	}

}