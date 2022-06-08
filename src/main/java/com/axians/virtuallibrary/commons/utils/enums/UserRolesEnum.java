package com.axians.virtuallibrary.commons.utils.enums;

import com.axians.virtuallibrary.commons.validations.exceptions.NotFoundUserRole;

public enum UserRolesEnum {
	
	ADMIN {
		@Override
		public String getRoleName() {
			return "ROLE_ADMIN";
		}
	},
	USER {
		@Override
		public String getRoleName() {
			return "ROLE_USER";
		}
	};
	
	public abstract String getRoleName();
	
	public static UserRolesEnum getEnumByName(String name) {
		for (UserRolesEnum role : UserRolesEnum.values()) {
			if (role.name().equalsIgnoreCase(name)) {
				return role;
			}
		}
		throw new NotFoundUserRole();
	}

}
