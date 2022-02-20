package com.bsf.bsfservice.api.boundry.dto;

import com.bsf.bsfservice.api.control.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransferResponseDTO {

	private Long transactionId;
	private TransactionStatus status;

}
