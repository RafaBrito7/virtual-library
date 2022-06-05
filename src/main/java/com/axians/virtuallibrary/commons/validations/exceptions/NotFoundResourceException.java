package com.axians.virtuallibrary.commons.validations.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundResourceException extends GenericResourceException {

	private static final long serialVersionUID = 1L;

	public NotFoundResourceException() {
		super(HttpStatus.NOT_FOUND, "Resource not found in database with this identifier!");
	}

}
