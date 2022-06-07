package com.axians.virtuallibrary.commons.validations.exceptions;

import org.springframework.http.HttpStatus;

public class BookUnavailableException extends GenericResourceException{

	private static final long serialVersionUID = 1L;

	public BookUnavailableException(String message) {
		super(HttpStatus.PRECONDITION_FAILED, message);
	}
}
