package com.axians.virtuallibrary.commons.utils.enums;

public enum StatusBookEnum {

	RENTED,
	AVAILABLE,
	DISABLED;
	
	public static StatusBookEnum getStatusBookEnum(final String status) {
		StatusBookEnum bookStatusEnum = null;
		for (StatusBookEnum statusEnum : StatusBookEnum.values()) {
			if (status.equalsIgnoreCase(statusEnum.name())) {
				bookStatusEnum = statusEnum;
			}
		}

		if (bookStatusEnum == null) {
			throw new IllegalArgumentException("No enum found to: " + status);
		}
		return bookStatusEnum;
	}
}
