package com.axians.virtuallibrary.commons.validations.exceptions.handlers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.axians.virtuallibrary.commons.utils.CustomErrorObject;
import com.axians.virtuallibrary.commons.utils.CustomErrorResponse;
import com.axians.virtuallibrary.commons.validations.exceptions.GenericResourceException;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(GenericResourceException.class)
    public ResponseEntity<String> handleGenericException(GenericResourceException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		CustomErrorResponse errorResponse = getErrorResponse(ex, status, getErrors(ex));
		return new ResponseEntity<>(errorResponse, status);
	}
	
	private List<CustomErrorObject> getErrors(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getFieldErrors().stream()
				.map(error -> new CustomErrorObject(error.getDefaultMessage(), error.getField()))
				.collect(Collectors.toList());
	}
	
	private CustomErrorResponse getErrorResponse(MethodArgumentNotValidException ex, HttpStatus status,
			List<CustomErrorObject> errors) {
		return new CustomErrorResponse(status.value(), status.getReasonPhrase(), "Invalid fields were sent",
				ex.getBindingResult().getObjectName(), errors);
	}
}
