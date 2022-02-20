package com.bsf.bsfservice.api.control.execption;

import lombok.Getter;

@Getter
public class RestException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** The transfer Service Error errors. */
	private RestError restError;

	/**
	 * Instantiates a new transfer Service exception.
	 *
	 * @param restError
	 */
	public RestException(final RestError restError) {
		super(restError.description());
		this.restError = restError;
	}
}
