package com.axians.virtuallibrary.commons.validations.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.axians.virtuallibrary.api.model.dto.UserDTO;
import com.axians.virtuallibrary.commons.utils.enums.UserRequiredPropertiesEnum;
import com.axians.virtuallibrary.commons.validations.ValidateStringIsInvalid;

public class ValidateUserException extends ValidateStringIsInvalid{
	
	private static Logger LOGGER = LoggerFactory.getLogger(ValidateUserException.class);
	
	public static void validate(UserDTO user) {
		LOGGER.info("Starting Validate User Parameters");
		execute(user.getEmail(), UserRequiredPropertiesEnum.EMAIL.name());
		execute(user.getName(), UserRequiredPropertiesEnum.NAME.name());
		execute(user.getPassword(), UserRequiredPropertiesEnum.PASSWORD.name());
		LOGGER.info("Parameters Validated with Success!");
	}
	
	private static void execute(String parameter, String parameterName) {
		if (isInvalid(parameter)) {
			LOGGER.error("The Parameter '" + parameterName + "' is Null");
			throw new GenericResourceException(HttpStatus.BAD_REQUEST,"The Parameter '" + parameterName + "' is Null");
		}
	}

}
