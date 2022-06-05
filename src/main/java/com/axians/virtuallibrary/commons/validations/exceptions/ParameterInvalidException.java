package com.axians.virtuallibrary.commons.validations.exceptions;

import org.springframework.http.HttpStatus;

public class ParameterInvalidException extends GenericResourceException{

	private static final long serialVersionUID = 1L;

	public ParameterInvalidException(String parameterName) {
		super(HttpStatus.BAD_REQUEST, "The Parameter '" + parameterName + "' is invalid");
	}

}
