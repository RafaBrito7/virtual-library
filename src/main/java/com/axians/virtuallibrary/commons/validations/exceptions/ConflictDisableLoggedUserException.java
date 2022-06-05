package com.axians.virtuallibrary.commons.validations.exceptions;

import org.springframework.http.HttpStatus;

public class ConflictDisableLoggedUserException extends GenericResourceException{

	private static final long serialVersionUID = 1L;

	public ConflictDisableLoggedUserException() {
		super(HttpStatus.CONFLICT, "Cannot disable your own user!");
	}

}
