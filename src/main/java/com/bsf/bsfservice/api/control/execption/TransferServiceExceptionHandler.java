package com.bsf.bsfservice.api.control.execption;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ControllerAdvice
public class TransferServiceExceptionHandler extends ResponseEntityExceptionHandler {
	/**
	 * Handle exception.
	 *
	 * @param exception the exception
	 * @return the response entity
	 */
	@ExceptionHandler(TransferServiceException.class)
	public final ResponseEntity<TransferServiceErrorResponse> handleException(final RestException exception) {
		final RestError restError = exception.getRestError();
		final TransferServiceErrorResponse errorResponse = TransferServiceErrorResponse.builder().errorMessage(restError.desceription()).error(restError.error())
				.status(restError.httpStatus().name()).build();
		return new ResponseEntity<>(errorResponse, restError.httpStatus());
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<TransferServiceErrorResponse> handleAllException(final Exception ex, final WebRequest request) {
		ex.printStackTrace();
		final TransferServiceError transferServiceError =  TransferServiceError.INTERNAL_SERVER_ERROR;
		final TransferServiceErrorResponse errorDetails = new TransferServiceErrorResponse(ex.getMessage(), transferServiceError.toString(),
				transferServiceError.httpStatus().toString(), LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
		return new ResponseEntity<>(errorDetails, transferServiceError.httpStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status,
																  final WebRequest request) {
		final TransferServiceError transferServiceError = TransferServiceError.REQUEST_VALIDATION_ERROR;
		final TransferServiceErrorResponse errorDetails = new TransferServiceErrorResponse(ex.getMessage(), transferServiceError.toString(),
				transferServiceError.httpStatus().toString(), LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
		return new ResponseEntity<>(errorDetails, transferServiceError.httpStatus());
	}

}
