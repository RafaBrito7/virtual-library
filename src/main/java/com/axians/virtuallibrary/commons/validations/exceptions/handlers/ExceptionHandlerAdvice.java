package com.axians.virtuallibrary.commons.validations.exceptions.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.axians.virtuallibrary.commons.validations.exceptions.GenericResourceException;

@ControllerAdvice
public class ExceptionHandlerAdvice {
	
	@ExceptionHandler(GenericResourceException.class)
    public ResponseEntity<String> handleException(GenericResourceException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }      
}
