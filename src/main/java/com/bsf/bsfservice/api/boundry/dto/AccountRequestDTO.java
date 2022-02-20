package com.bsf.bsfservice.api.boundry.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
public class AccountRequestDTO {
	@NotNull(message="email cannot be missing")
	@NotEmpty(message="email cannot be empty")
	@NotBlank(message="email must contain at least one non whitespace character")
	private String email;

	@NotNull(message="balance cannot be missing")
	private BigDecimal balance;
}
