package com.axians.virtuallibrary.commons.validations.exceptions.validates;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axians.virtuallibrary.api.model.entity.User;
import com.axians.virtuallibrary.commons.validations.ValidateObjectIsInvalid;
import com.axians.virtuallibrary.commons.validations.exceptions.NotFoundResourceException;

public class ValidateUserNotFoundException extends ValidateObjectIsInvalid{
	
	private static Logger logger = LoggerFactory.getLogger(ValidateUserNotFoundException.class);
	
	public static void validate(Optional<User> user) {
		if (isObjectNull(user)) {
			logger.error("No user found with this username in the database");
			throw new NotFoundResourceException();
		}
	}
}
