package com.axians.virtuallibrary.commons.utils.enums;

import com.axians.virtuallibrary.commons.validations.exceptions.NotFoundUserRole;

public enum UserRolesEnum {

	ROOT {
		@Override
		public String getRoleName() {
			return "ROLE_ROOT";
		}
	},
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
	},
	VIEWER {
		@Override
		public String getRoleName() {
			return "ROLE_VIEWER";
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
