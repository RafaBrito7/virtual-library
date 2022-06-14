package com.axians.virtuallibrary.commons.validations.exceptions.validates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axians.virtuallibrary.api.model.dto.BookDTO;
import com.axians.virtuallibrary.commons.utils.enums.BookRequiredPropertiesEnum;
import com.axians.virtuallibrary.commons.validations.ValidateStringIsInvalid;
import com.axians.virtuallibrary.commons.validations.exceptions.ObjectInvalidException;
import com.axians.virtuallibrary.commons.validations.exceptions.ParameterInvalidException;

public class ValidateBookException extends ValidateStringIsInvalid{

	private static Logger logger = LoggerFactory.getLogger(ValidateBookException.class);
	
	public static void validate(BookDTO book) {
		logger.info("Starting Validate if Book have strong parameters");
		validateObjectIsNull(book);
		validateParameters(book);
		logger.info("Book validated with success!");
	}
	
	private static void validateObjectIsNull(BookDTO book) {
		if (book == null) {
			logger.error("Book is null!");
			throw new ObjectInvalidException("Book");
		}
	}

	private static void validateParameters(BookDTO book) {
		logger.info("Starting Validate Book Parameters");
		execute(book.getCategory(), BookRequiredPropertiesEnum.CATEGORY.name());
		execute(book.getTitle(), BookRequiredPropertiesEnum.TITLE.name());
		logger.info("Parameters Validated with Success!");
	}
	
	private static void execute(String parameter, String parameterName) {
		if (isInvalid(parameter)) {
			logger.error("The Parameter '" + parameterName + "' is Null");
			throw new ParameterInvalidException(parameterName);
		}
	}
}
