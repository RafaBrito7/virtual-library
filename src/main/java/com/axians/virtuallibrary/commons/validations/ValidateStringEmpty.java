package com.axians.virtuallibrary.commons.validations;

public class ValidateStringEmpty {
	
	public static Boolean isParameterEmpty(String param) {
		if (param.isEmpty()) {
			return true;
		}
		return false;
	}

}
