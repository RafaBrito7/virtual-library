package com.axians.virtuallibrary.commons.utils.enums;

public enum CategoryBookEnum {

	ACTION_ADVENTURE {
		@Override
		public String getDescription() {
			return "Action Adventure";
		}
	},
	CLASSICS {
		@Override
		public String getDescription() {
			return "Classics";
		}
	},
	COMIC_BOOK {
		@Override
		public String getDescription() {
			return "Comic Book";
		}
	},
	DETECTIVE {
		@Override
		public String getDescription() {
			return "Detective";
		}
	},
	FANTASY {
		@Override
		public String getDescription() {
			return "Fantasy";
		}
	},
	HORROR {
		@Override
		public String getDescription() {
			return "Horror";
		}
	},
	ROMANCE {
		@Override
		public String getDescription() {
			return "Romance";
		}
	},
	HUMOR {
		@Override
		public String getDescription() {
			return "Humor";
		}
	},
	SCIENCE_FICTION {
		@Override
		public String getDescription() {
			return "Science Fiction";
		}
	},
	SUSPENSE {
		@Override
		public String getDescription() {
			return "Suspense";
		}
	},
	BIOGRAPHIES {
		@Override
		public String getDescription() {
			return "Biographies";
		}
	},
	COOKBOOKS {
		@Override
		public String getDescription() {
			return "Cookbooks";
		}
	},
	HISTORY {
		@Override
		public String getDescription() {
			return "History";
		}
	},
	MEMOIR {
		@Override
		public String getDescription() {
			return "Memoir";
		}
	},
	POETRY {
		@Override
		public String getDescription() {
			return "Poetry";
		}
	},
	SELF_HELP {
		@Override
		public String getDescription() {
			return "Self Help";
		}
	},
	TRUE_CRIME {
		@Override
		public String getDescription() {
			return "True Crime";
		}
	};

	public abstract String getDescription();

	public static CategoryBookEnum getStatusBookEnum(final String category) {
		for (CategoryBookEnum statusEnum : CategoryBookEnum.values()) {
			if (category.equalsIgnoreCase(statusEnum.name())) {
				return statusEnum;
			}
		}
		throw new IllegalArgumentException("No enum found to: " + category);
	}
}
