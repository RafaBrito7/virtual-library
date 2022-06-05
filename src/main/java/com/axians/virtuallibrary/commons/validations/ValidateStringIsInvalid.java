package com.axians.virtuallibrary.commons.validations;

public class ValidateStringIsInvalid {
	
	public static Boolean isInvalid(String param) {
		if (param == null || param.isEmpty()) {
			return true;
		}
		return false;
	}

}
