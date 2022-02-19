package com.bsf.bsfservice.api.control;

import com.bsf.bsfservice.api.boundry.dto.AccountDetailsDTO;
import com.bsf.bsfservice.api.boundry.dto.AccountRequestDTO;
import com.bsf.bsfservice.api.control.execption.TransferServiceError;
import com.bsf.bsfservice.api.entity.Account;
import com.bsf.bsfservice.api.entity.AccountStatus;
import com.bsf.bsfservice.api.entity.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

	private AccountRepository accountRepository;

	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}


	public AccountDetailsDTO getAccountDetails(Long accountId) {
		Optional<Account> optionalAccount = accountRepository.findById(accountId);
		if (optionalAccount.isEmpty()) {
			throw TransferServiceError.ACCOUNT_NOT_EXIST.buildExcpetion();
		}
		Account account = optionalAccount.get();
		return AccountDetailsDTO.builder().accountId(account.getId()).email(account.getEmail())
				.balance(account.getBalance()).status(account.getAccountStatus()).build();
	}

	public AccountDetailsDTO createNewAccount(AccountRequestDTO accountRequestDTO) {
		Account account = Account.builder().accountStatus(AccountStatus.ACTIVE.name()).email(accountRequestDTO.getEmail())
				.balance(accountRequestDTO.getBalance()).build();
		Account savedAccount = accountRepository.save(account);
		return AccountDetailsDTO.builder().accountId(savedAccount.getId()).email(savedAccount.getEmail())
				.balance(savedAccount.getBalance()).status(savedAccount.getAccountStatus()).build();
	}
}
