package com.axians.virtuallibrary.commons.validations.exceptions;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.axians.virtuallibrary.commons.model.entity.User;
import com.axians.virtuallibrary.commons.validations.ValidateObjectNull;

public class ValidateUserNotFoundException extends ValidateObjectNull{
	
	private static Logger LOGGER = LoggerFactory.getLogger(ValidateUserNotFoundException.class);
	
	public static void validate(Optional<User> user) {
		if (isObjectNull(user)) {
			LOGGER.error("No user found with this username in the database");
			throw new UsernameNotFoundException("No user found with this username in the database");
		}
	}
}
