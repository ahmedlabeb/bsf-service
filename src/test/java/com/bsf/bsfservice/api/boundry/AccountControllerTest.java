package com.bsf.bsfservice.api.boundry;

import com.bsf.bsfservice.api.boundry.dto.AccountDetailsDTO;
import com.bsf.bsfservice.api.boundry.dto.AccountRequestDTO;
import com.bsf.bsfservice.api.control.AccountService;
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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

	@MockBean
	AccountService accountService;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	private final ObjectMapper mapper = new ObjectMapper();

	@Mock
	Logger log;

	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void testAccountDetails_successful() throws Exception {
		lenient().when(accountService.getAccountDetails(any(Long.class))).thenReturn(new AccountDetailsDTO());
		mockMvc.perform(get("/api/account").queryParam("accountId","1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testAccountDetails_missing_accountId_in_request() throws Exception {
		mockMvc.perform(get("/account")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testAccountDetails_accountId_notfound() throws Exception {
		lenient().when(accountService.getAccountDetails(any())).thenThrow(TransferServiceError.ACCOUNT_NOT_EXIST.buildExcpetion());
		mockMvc.perform(get("/api/account")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	@Test
	void testCreateNewAccount_successful() throws Exception {
		lenient().when(accountService.createNewAccount(buildAccountRequestDto())).thenReturn(new AccountDetailsDTO());
		mockMvc.perform(post("/api/account")
						.content(mapper.writeValueAsString(buildAccountRequestDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testCreateNewAccount_missing_attribute_badRequest() throws Exception {
		mockMvc.perform(post("/api/account")
						.content(mapper.writeValueAsString(buildInvalidAccountRequestDto()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	private AccountRequestDTO buildAccountRequestDto() {
		return AccountRequestDTO.builder().balance(new BigDecimal(10)).email("aa@aa").build();
	}
	private AccountRequestDTO buildInvalidAccountRequestDto() {
		return AccountRequestDTO.builder().build();
	}
}
