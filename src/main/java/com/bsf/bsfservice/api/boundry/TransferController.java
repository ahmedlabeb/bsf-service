package com.bsf.bsfservice.api.boundry;

import com.bsf.bsfservice.api.boundry.dto.TransferRequestDTO;
import com.bsf.bsfservice.api.boundry.dto.TransferResponseDTO;
import com.bsf.bsfservice.api.boundry.helper.ResponseUtil;
import com.bsf.bsfservice.api.control.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
@Api(value = "Transfer Enpoint")
public class TransferController {

	private TransferService accountService;

	public TransferController(TransferService accountService) {
		this.accountService = accountService;
	}

	@PostMapping("/transfer")
	@ApiOperation(value = "transfer money  ", response = ResponseEntity.class)
	public ResponseEntity<?> transferMoney(@Validated @RequestBody TransferRequestDTO transferRequestDTO){
		TransferResponseDTO transferResponseDTO = accountService.transferMoney(transferRequestDTO);
		return ResponseUtil.wrapOrNotFound(transferResponseDTO);

	}
}
