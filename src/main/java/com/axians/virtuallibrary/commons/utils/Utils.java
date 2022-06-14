package com.axians.virtuallibrary.commons.utils;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Utils {
	
	private Utils() {
		throw new IllegalStateException("Utility class");
	}
	
	public static String generateResourceHyperIdentifier() {
		return UUID.randomUUID().toString();
	}
	
	public static String encoderPassword(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}

}
