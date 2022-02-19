package com.bsf.bsfservice.api.control.execption;

import lombok.Getter;

@Getter
public class TransferServiceException extends RestException {

	private static final long serialVersionUID = -98729045756876L;

	private TransferServiceError transferServiceError;

	public TransferServiceException(final TransferServiceError transferServiceError) {
		super(transferServiceError);
		this.transferServiceError = transferServiceError;
	}
}
