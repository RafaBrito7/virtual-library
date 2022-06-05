package com.axians.virtuallibrary.commons.validations.exceptions;

import org.springframework.http.HttpStatus;

public class ConflictResourceException extends GenericResourceException {

	private static final long serialVersionUID = 1L;

	public ConflictResourceException() {
		super(HttpStatus.CONFLICT, "This resource already exists in database!");
	}

}
