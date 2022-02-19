package com.bsf.bsfservice.api.boundry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsDTO {
	private Long accountId;
	private BigDecimal balance;
	private String email;
	private String status;
}
