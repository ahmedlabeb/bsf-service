package com.bsf.bsfservice.api.control.execption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferServiceErrorResponse {
	/** The error message . */
	private String errorMessage;

	/** The error Code . */
	private String error;

	/** The status. */
	private String status;
	/** Request time Stamp */
	private Long timestamp;
}
