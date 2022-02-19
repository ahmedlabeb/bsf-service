package com.bsf.bsfservice.api.control;

import com.bsf.bsfservice.api.boundry.dto.TransferRequestDTO;
import com.bsf.bsfservice.api.boundry.dto.TransferResponseDTO;
import com.bsf.bsfservice.api.control.execption.TransferServiceException;
import com.bsf.bsfservice.api.entity.*;
import com.bsf.bsfservice.api.entity.repository.AccountRepository;
import com.bsf.bsfservice.api.entity.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {

	@InjectMocks
	private TransferService transferService;

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private TransactionRepository transactionRepository;

	@Test
	public void testTransferMoney_success(){
		Account account1=Account.builder().accountStatus(AccountStatus.ACTIVE.name())
				.email("aa@aa").balance(new BigDecimal(10)).id(1L).build();
		Account account2=Account.builder().accountStatus(AccountStatus.ACTIVE.name())
				.email("bb@bb").balance(new BigDecimal(20)).id(2L).build();
		Transaction transaction = buildTransaction(account1.getId(), account2.getId());
		Transaction savedTransaction = buildSavedTransaction(transaction);

		TransferRequestDTO transferRequestDTO = buildTransferRequestDto();
		lenient().when(accountRepository.findById(1L)).thenReturn(Optional.of(account1));
		lenient().when(accountRepository.findById(2L)).thenReturn(Optional.of(account2));
		lenient().when(transactionRepository.save(any())).thenReturn(savedTransaction);
		TransferResponseDTO transferResponseDTO = transferService.transferMoney(transferRequestDTO);
		verify(accountRepository).save(account1);
		verify(accountRepository).save(account2);

		assertEquals(transferResponseDTO.getStatus().name(),TransactionStatus.SUCCESS.name());
		assertEquals(account1.getBalance(),new BigDecimal(5));
		assertEquals(account2.getBalance(),new BigDecimal(25));

	}

	private Transaction buildSavedTransaction(Transaction transaction) {
		 transaction.setId(1L);
		 return transaction;
	}

	@Test
	public void testTransferMoney_accountNotFound(){
		Account account1=Account.builder().accountStatus(AccountStatus.ACTIVE.name())
				.email("aa@aa").balance(new BigDecimal(10)).id(1L).build();
		Account account2=Account.builder().accountStatus(AccountStatus.ACTIVE.name())
				.email("bb@bb").balance(new BigDecimal(20)).id(2L).build();
		TransferRequestDTO transferRequestDTO = buildTransferRequestDto();
		lenient().when(accountRepository.findById(1L)).thenReturn(Optional.empty());
		lenient().when(accountRepository.findById(2L)).thenReturn(Optional.of(account2));
		assertThrows(TransferServiceException.class,
				() ->    transferService.transferMoney(transferRequestDTO));
	}

	@Test
	public void testTransferMoney_sameAccounts(){
		Account account1=Account.builder().accountStatus(AccountStatus.ACTIVE.name())
				.email("aa@aa").balance(new BigDecimal(10)).id(1L).build();
		Account account2=Account.builder().accountStatus(AccountStatus.ACTIVE.name())
				.email("bb@bb").balance(new BigDecimal(20)).id(2L).build();
		TransferRequestDTO transferRequestDTO = buildTransferRequestDto();
		lenient().when(accountRepository.findById(1L)).thenReturn(Optional.of(account1));
		lenient().when(accountRepository.findById(2L)).thenReturn(Optional.of(account1));
		assertThrows(TransferServiceException.class,
				() ->    transferService.transferMoney(transferRequestDTO));
	}

	@Test
	public void testTransferMoney_Insuffeict_balance(){
		Account account1=Account.builder().accountStatus(AccountStatus.ACTIVE.name())
				.email("aa@aa").balance(new BigDecimal(1)).id(1L).build();
		Account account2=Account.builder().accountStatus(AccountStatus.ACTIVE.name())
				.email("bb@bb").balance(new BigDecimal(20)).id(2L).build();
		TransferRequestDTO transferRequestDTO = buildTransferRequestDto();
		lenient().when(accountRepository.findById(1L)).thenReturn(Optional.of(account1));
		lenient().when(accountRepository.findById(2L)).thenReturn(Optional.of(account2));
		assertThrows(TransferServiceException.class,
				() ->    transferService.transferMoney(transferRequestDTO));
	}

	private List<Account> buildListOfAccounts() {
		Account account1 = Account.builder().accountStatus(AccountStatus.ACTIVE.name()).email("aa@aa")
				.balance(new BigDecimal(10)).build();
		Account account2 = Account.builder().accountStatus(AccountStatus.ACTIVE.name()).email("bb@bb")
				.balance(new BigDecimal(20)).build();
		return List.of(account1, account2);
	}

	private TransferRequestDTO buildTransferRequestDto(){
		return TransferRequestDTO.builder().amount(new BigDecimal(5)).toAccountId(2L).fromAccountId(1L).build();
	}

	private Transaction buildTransaction(Long fromAccountId,Long toAccountId){
		return Transaction.builder().fromAccountId(fromAccountId).toAccountId(toAccountId).action(TransactionAction.TRANSFER.name())
				.status(TransactionStatus.SUCCESS.name()).createdDate(Instant.now()).updatedDate(Instant.now()).build();
	}

}
