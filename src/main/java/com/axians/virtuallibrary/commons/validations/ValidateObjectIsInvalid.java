package com.axians.virtuallibrary.commons.validations;

import java.util.Optional;

public class ValidateObjectNull {
	
	public static Boolean isObjectNull(Optional<?> object) {
		if (object.isEmpty()) {
			return true;
		}
		return false;
	}
}
