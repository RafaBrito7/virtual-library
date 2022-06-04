package com.axians.virtuallibrary.commons.validations.exceptions;

import java.security.InvalidParameterException;

import com.axians.virtuallibrary.commons.validations.ValidateStringEmpty;

public class ValidateParameterEmptyException extends ValidateStringEmpty{

	public static void validate(String parameter) {
		if (isParameterEmpty(parameter)) {
			throw new InvalidParameterException("The parameter is Empty!");
		}
	}
}
