package com.axians.virtuallibrary.commons.validations.exceptions.validates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.axians.virtuallibrary.commons.validations.ValidateStringIsInvalid;
import com.axians.virtuallibrary.commons.validations.exceptions.GenericResourceException;

public class ValidateParameterEmptyException extends ValidateStringIsInvalid{
	
	private static Logger logger = LoggerFactory.getLogger(ValidateParameterEmptyException.class);

	public static void validate(String parameter, String parameterName) {
		if (isInvalid(parameter)) {
			logger.error("The parameter is Empty!");
			throw new GenericResourceException(HttpStatus.BAD_REQUEST, "The parameter '" + parameterName + "' is Empty!");
		}
		logger.info("Parameters validated with sucess!");
	}
}
