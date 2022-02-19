package com.bsf.bsfservice.api.boundry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequestDTO {
	@NotNull(message="fromAccountId cannot be missing")
	private Long fromAccountId;

	@NotNull(message="toAccountId cannot be missing")
	private Long toAccountId;

	@NotNull(message="balance cannot be missing")
	private BigDecimal amount;
}
