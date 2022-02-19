package com.bsf.bsfservice.api.control.execption;

import org.springframework.http.HttpStatus;

public interface RestError {
	String error();

	HttpStatus httpStatus();

	String desceription();
}
