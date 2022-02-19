package com.bsf.bsfservice.api.boundry;

import com.bsf.bsfservice.api.boundry.dto.AccountDetailsDTO;
import com.bsf.bsfservice.api.boundry.dto.AccountRequestDTO;
import com.bsf.bsfservice.api.boundry.helper.ResponseUtil;
import com.bsf.bsfservice.api.control.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@Api(value = "Account Endpoint")
public class AccountController {

	private AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("/account")
	@ApiOperation(value = "get account details   ", response = ResponseEntity.class)
	public ResponseEntity<?> accountDetails(@RequestParam(value = "accountId") Long accountId) {
		AccountDetailsDTO accountDetails = accountService.getAccountDetails(accountId);
		return ResponseUtil.wrapOrNotFound(accountDetails);
	}

	@PostMapping("/account")
	@ApiOperation(value = "create new account", response = ResponseEntity.class)
	public ResponseEntity<?> createNewAccount(@Validated @RequestBody AccountRequestDTO accountRequestDTO) {
		AccountDetailsDTO accountDetails = accountService.createNewAccount(accountRequestDTO);
		return ResponseUtil.wrapOrNotFound(accountDetails);
	}
}
