package com.axians.virtuallibrary.commons.utils;

public class CustomErrorObject {

	private String detailMessage;

	private String field;

	public CustomErrorObject(String detailMessage, String field) {
		this.detailMessage = detailMessage;
		this.field = field;
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
