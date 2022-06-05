package com.axians.virtuallibrary.commons.validations.exceptions;

import org.springframework.http.HttpStatus;

public class GenericResourceException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public GenericResourceException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
