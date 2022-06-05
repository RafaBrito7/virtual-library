package com.axians.virtuallibrary.commons.validations.exceptions;

import org.springframework.http.HttpStatus;

public class ObjectInvalidException extends GenericResourceException{

	private static final long serialVersionUID = 1L;
	
	public ObjectInvalidException(String objectName) {
		super(HttpStatus.BAD_REQUEST, "The " + objectName + " is null!");
	}

}
