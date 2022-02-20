package com.bsf.bsfservice.api.control;

import com.bsf.bsfservice.api.boundry.dto.AccountDetailsDTO;
import com.bsf.bsfservice.api.boundry.dto.AccountRequestDTO;
import com.bsf.bsfservice.api.control.execption.TransferServiceException;
import com.bsf.bsfservice.api.entity.Account;
import com.bsf.bsfservice.api.control.enums.AccountStatus;
import com.bsf.bsfservice.api.entity.repository.AccountRepository;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.lenient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

	@InjectMocks
	private AccountService accountService;

	@Mock
	private AccountRepository accountRepository;

	@Test
	public void testGetAccountDetails() {
		Optional<Account> accountOptional = Optional.of(buildAccount());
		lenient().when(accountRepository.findById(1L)).thenReturn(accountOptional);
		AccountDetailsDTO accountDetails = accountService.getAccountDetails(1L);
		assertTrue(accountOptional.isPresent());
		assertEquals(accountDetails.getAccountId(),accountOptional.get().getId());
		assertEquals(accountDetails.getEmail(),accountOptional.get().getEmail());
	}

	@Test
	public void testGetAccountDetails_accountNotFound() {
		Optional<Account> accountOptional = Optional.empty();
		lenient().when(accountRepository.findById(10L)).thenReturn(accountOptional);
		assertThrows(TransferServiceException.class,
				() -> accountService.getAccountDetails(1L));
	}

	@Test
	public void testCreateNewAccount() {
		Account savedAccoount=Account.builder().id(3L).accountStatus(AccountStatus.ACTIVE.name()).
				email("aa@aa").balance(new BigDecimal(10)).build();
		lenient().when(accountRepository.save(buildAccount())).thenReturn(savedAccoount);
		AccountDetailsDTO accountDetails = accountService.createNewAccount(buildAccountDetailDTO());
		assertEquals(accountDetails.getEmail(),savedAccoount.getEmail());
		assertEquals(accountDetails.getStatus(),AccountStatus.ACTIVE.name());
		assertEquals(accountDetails.getBalance(),savedAccoount.getBalance());
	}

	private List<Account> buildListOfAccounts() {
		Account account1 = Account.builder().accountStatus(AccountStatus.ACTIVE.name()).email("aa@aa")
				.balance(new BigDecimal(10)).build();
		Account account2 = Account.builder().accountStatus(AccountStatus.ACTIVE.name()).email("bb@bb")
				.balance(new BigDecimal(20)).build();
		return List.of(account1, account2);
	}

	private AccountRequestDTO buildAccountDetailDTO(){
		return AccountRequestDTO.builder().email("aa@aa").balance(new BigDecimal(10)).build();
	}
	private Account buildAccount() {
		return Account.builder().accountStatus(AccountStatus.ACTIVE.name()).email("aa@aa")
				.balance(new BigDecimal(10)).build();
	}

}
