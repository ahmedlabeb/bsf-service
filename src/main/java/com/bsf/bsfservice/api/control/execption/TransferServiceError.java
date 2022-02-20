package com.bsf.bsfservice.api.control.execption;

import org.springframework.http.HttpStatus;

public enum TransferServiceError implements RestError {


	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "unexpected error"),

	ACCOUNT_NOT_EXIST(HttpStatus.NOT_FOUND, "Account id not exist"),

	INVALID_TRANSACTION(HttpStatus.BAD_REQUEST, "Can't transfer money from one account to same account"),

	REQUEST_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, ""),

	INSUFFICIENT_BALANCE(HttpStatus.FORBIDDEN,"User don't have enough balance");

	TransferServiceError(HttpStatus httpStatus, String description) {
		this.httpStatus = httpStatus;
		this.description = description;
	}

	/**
	 * The http status.
	 */
	private HttpStatus httpStatus;

	/**
	 * The description.
	 */
	private String description;

	@Override
	public String error() {
		return this.name();
	}

	@Override
	public HttpStatus httpStatus() {
		return this.httpStatus;
	}

	@Override
	public String description() {
		return this.description;
	}

	public RestException buildExcpetion() {
		return new TransferServiceException(this);
	}
}
