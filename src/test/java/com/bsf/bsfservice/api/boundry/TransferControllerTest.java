package com.bsf.bsfservice.api.boundry;

import com.bsf.bsfservice.api.boundry.dto.AccountRequestDTO;
import com.bsf.bsfservice.api.boundry.dto.TransferRequestDTO;
import com.bsf.bsfservice.api.boundry.dto.TransferResponseDTO;
import com.bsf.bsfservice.api.control.AccountService;
import com.bsf.bsfservice.api.control.TransferService;
import com.bsf.bsfservice.api.control.execption.TransferServiceError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransferControllerTest {

	@MockBean
	TransferService transferService;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private AccountService accountService;


	private final ObjectMapper mapper = new ObjectMapper();

	@Mock
	Logger log;

	@BeforeEach
	public void setUp() {
		accountService.createNewAccount(AccountRequestDTO.builder().email("aa@aa")
				.balance(new BigDecimal(10)).build());
		accountService.createNewAccount(AccountRequestDTO.builder().email("bb@bb")
				.balance(new BigDecimal(10)).build());

		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void testTransferMoney_successful() throws Exception {
		lenient().when(transferService.transferMoney(buildTransferRequestDTO())).thenReturn(new TransferResponseDTO());
		mockMvc.perform(post("/api/transfer")
						.content(mapper.writeValueAsString(buildTransferRequestDTO()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}


	@Test
	void testTransferMoney_insufficient_balance() throws Exception {
		lenient().when(transferService.transferMoney(buildTransferRequestDTO())).thenThrow(TransferServiceError.INSUFFICIENT_BALANCE.buildExcpetion());
		mockMvc.perform(post("/api/transfer")
						.content(mapper.writeValueAsString(buildTransferRequestDTO()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	private TransferRequestDTO buildTransferRequestDTO() {
		return TransferRequestDTO.builder().fromAccountId(1L).toAccountId(2L).amount(new BigDecimal(5)).build();
	}

}
