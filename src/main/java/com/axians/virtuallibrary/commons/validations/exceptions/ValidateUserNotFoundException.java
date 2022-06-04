package com.axians.virtuallibrary.commons.validations.exceptions;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.axians.virtuallibrary.commons.model.entity.User;
import com.axians.virtuallibrary.commons.validations.ValidateObjectNull;

public class ValidateUserNotFoundException extends ValidateObjectNull{
	
	public static void validate(Optional<User> user) {
		if (isObjectNull(user)) {
			throw new UsernameNotFoundException("No user found with this username in the database");
		}
	}
}
