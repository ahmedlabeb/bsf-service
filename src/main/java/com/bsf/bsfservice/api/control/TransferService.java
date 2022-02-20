package com.bsf.bsfservice.api.control;

import com.bsf.bsfservice.api.boundry.dto.TransferRequestDTO;
import com.bsf.bsfservice.api.boundry.dto.TransferResponseDTO;
import com.bsf.bsfservice.api.control.execption.TransferServiceError;
import com.bsf.bsfservice.api.entity.Account;
import com.bsf.bsfservice.api.entity.Transaction;
import com.bsf.bsfservice.api.control.enums.TransactionAction;
import com.bsf.bsfservice.api.control.enums.TransactionStatus;
import com.bsf.bsfservice.api.entity.repository.AccountRepository;
import com.bsf.bsfservice.api.entity.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class TransferService {

	private AccountRepository accountRepository;
	private TransactionRepository transactionRepository;

	public TransferService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
	}

	@Transactional
	public TransferResponseDTO transferMoney(TransferRequestDTO transferRequestDTO) {

		if (transferRequestDTO.getFromAccountId().equals(transferRequestDTO.getToAccountId())) {
			throw TransferServiceError.INVALID_TRANSACTION.buildExcpetion();
		}

		Optional<Account> fromAccountOptional = accountRepository.findById(transferRequestDTO.getFromAccountId());
		Optional<Account> toAccountOptional = accountRepository.findById(transferRequestDTO.getToAccountId());

		if (fromAccountOptional.isEmpty() || toAccountOptional.isEmpty()) {
			throw TransferServiceError.ACCOUNT_NOT_EXIST.buildExcpetion();
		}

		Account fromAccount = fromAccountOptional.get();
		Account toAccount = toAccountOptional.get();
		synchronized (this) {
			if (fromAccount.getBalance().compareTo(transferRequestDTO.getAmount()) >= 0) {
				fromAccount.setBalance(fromAccount.getBalance().subtract(transferRequestDTO.getAmount()));
				toAccount.setBalance(toAccount.getBalance().add(transferRequestDTO.getAmount()));
				accountRepository.save(fromAccount);
				accountRepository.save(toAccount);
				Transaction transaction = logTransaction(fromAccount.getId(), toAccount.getId(), TransactionStatus.SUCCESS);
				return TransferResponseDTO.builder().transactionId(transaction.getId())
						.status(TransactionStatus.SUCCESS).build();
			} else {
				logTransaction(fromAccount.getId(), toAccount.getId(), TransactionStatus.FAILED);
				throw TransferServiceError.INSUFFICIENT_BALANCE.buildExcpetion();
			}
		}
	}

	private synchronized Transaction logTransaction(Long fromAccountId, Long toAccountId, TransactionStatus status) {
		Transaction transaction = Transaction.builder().fromAccountId(fromAccountId).toAccountId(toAccountId).action(TransactionAction.TRANSFER.name())
				.status(status.name()).createdDate(Instant.now()).updatedDate(Instant.now()).build();
		return transactionRepository.save(transaction);
	}


}
