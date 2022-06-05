package com.axians.virtuallibrary.commons.validations.exceptions;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.axians.virtuallibrary.api.model.entity.User;
import com.axians.virtuallibrary.commons.validations.ValidateObjectIsInvalid;

public class ValidateUserNotFoundException extends ValidateObjectIsInvalid{
	
	private static Logger LOGGER = LoggerFactory.getLogger(ValidateUserNotFoundException.class);
	
	public static void validate(Optional<User> user) {
		if (isObjectNull(user)) {
			LOGGER.error("No user found with this username in the database");
			throw new GenericResourceException(HttpStatus.BAD_REQUEST,"No user found with this username in the database");
		}
	}
}
