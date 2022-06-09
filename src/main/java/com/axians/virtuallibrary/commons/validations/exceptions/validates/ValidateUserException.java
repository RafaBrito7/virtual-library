package com.axians.virtuallibrary.commons.validations.exceptions.validates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axians.virtuallibrary.api.model.dto.UserDTO;
import com.axians.virtuallibrary.commons.utils.enums.UserRequiredPropertiesEnum;
import com.axians.virtuallibrary.commons.validations.ValidateStringIsInvalid;
import com.axians.virtuallibrary.commons.validations.exceptions.ObjectInvalidException;
import com.axians.virtuallibrary.commons.validations.exceptions.ParameterInvalidException;

public class ValidateUserException extends ValidateStringIsInvalid{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ValidateUserException.class);
	
	public static void validate(UserDTO user) {
		LOGGER.info("Starting Validate if User have strong parameters");
		validateObjectIsNull(user);
		validateParameters(user);
		LOGGER.info("User validated with success!");
	}

	private static void validateObjectIsNull(UserDTO user) {
		if (user == null) {
			LOGGER.error("User is null!");
			throw new ObjectInvalidException("User");
		}
	}

	private static void validateParameters(UserDTO user) {
		LOGGER.info("Starting Validate User Parameters");
		execute(user.getEmail(), UserRequiredPropertiesEnum.EMAIL.name());
		execute(user.getName(), UserRequiredPropertiesEnum.NAME.name());
		execute(user.getPassword(), UserRequiredPropertiesEnum.PASSWORD.name());
		execute(user.getProfile().name(), UserRequiredPropertiesEnum.PROFILE.name());
		LOGGER.info("Parameters Validated with Success!");
	}
	
	private static void execute(String parameter, String parameterName) {
		if (isInvalid(parameter)) {
			LOGGER.error("The Parameter '" + parameterName + "' is Null");
			throw new ParameterInvalidException(parameterName);
		}
	}

}
