package com.axians.virtuallibrary.commons.validations.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundUserRole extends GenericResourceException{

	private static final long serialVersionUID = 1L;

	public NotFoundUserRole() {
		super(HttpStatus.NOT_FOUND, "Not found Role for user with the parameter selected!");
	}
}
