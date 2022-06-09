package com.axians.virtuallibrary.commons.utils;

import java.util.List;

public class CustomErrorResponse {

	private Integer code;

	private String status;

	private String genericMessage;

	private String objectName;

	List<CustomErrorObject> errors;

	public CustomErrorResponse(Integer code, String status, String genericMessage, String objectName,
			List<CustomErrorObject> errors) {
		this.code = code;
		this.status = status;
		this.genericMessage = genericMessage;
		this.objectName = objectName;
		this.errors = errors;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGenericMessage() {
		return genericMessage;
	}

	public void setGenericMessage(String genericMessage) {
		this.genericMessage = genericMessage;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public List<CustomErrorObject> getErrors() {
		return errors;
	}

	public void setErrors(List<CustomErrorObject> errors) {
		this.errors = errors;
	}

}
