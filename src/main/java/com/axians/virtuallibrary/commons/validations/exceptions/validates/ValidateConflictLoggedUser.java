package com.axians.virtuallibrary.commons.validations.exceptions.validates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axians.virtuallibrary.api.model.entity.User;
import com.axians.virtuallibrary.api.model.entity.UserSpringSecurity;
import com.axians.virtuallibrary.commons.validations.exceptions.ConflictDisableLoggedUserException;

public class ValidateConflictLoggedUser {
	
	private static Logger logger = LoggerFactory.getLogger(ValidateConflictLoggedUser.class);
	
	public static void validate(User userDataBase, UserSpringSecurity loggedUser) {
		if (userDataBase.getResourceHyperIdentifier().equals(loggedUser.getResourceHyperIdentifier())) {
			logger.error("Not allowed to disable your own user");
			throw new ConflictDisableLoggedUserException();
		}
	}

}
