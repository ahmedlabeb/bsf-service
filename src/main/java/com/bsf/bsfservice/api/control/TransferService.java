package com.bsf.bsfservice.api.control;

import com.bsf.bsfservice.api.boundry.dto.TransferRequestDTO;
import com.bsf.bsfservice.api.boundry.dto.TransferResponseDTO;
import com.bsf.bsfservice.api.control.execption.TransferServiceError;
import com.bsf.bsfservice.api.entity.Account;
import com.bsf.bsfservice.api.entity.Transaction;
import com.bsf.bsfservice.api.entity.TransactionAction;
import com.bsf.bsfservice.api.entity.TransactionStatus;
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

		Optional<Account> fromAccountOptional = accountRepository.findById(transferRequestDTO.getFromAccountId());
		Optional<Account> toAccountOptional = accountRepository.findById(transferRequestDTO.getToAccountId());
		if (fromAccountOptional.isEmpty() || toAccountOptional.isEmpty()) {
			throw TransferServiceError.ACCOUNT_NOT_EXIST.buildExcpetion();
		}

		Account fromAccount = fromAccountOptional.get();
		Account toAccount = toAccountOptional.get();

		if (fromAccount.getId().equals(toAccount.getId())) {
			throw TransferServiceError.INVALID_TRANSACTION.buildExcpetion();
		}

		if (fromAccount.getBalance().compareTo(transferRequestDTO.getAmount()) > 0) {
			fromAccount.setBalance(fromAccount.getBalance().subtract(transferRequestDTO.getAmount()));
			toAccount.setBalance(toAccount.getBalance().add(transferRequestDTO.getAmount()));
			accountRepository.save(fromAccount);
			accountRepository.save(toAccount);
			Transaction transaction = logTransaction(fromAccount.getId(), toAccount.getId());
			return TransferResponseDTO.builder().transactionId(transaction.getId())
					.status(TransactionStatus.SUCCESS).build();
		} else {
			throw TransferServiceError.INSUFFIECIENT_BALANCE.buildExcpetion();
		}
	}

	private Transaction logTransaction(Long fromAccountId, Long toAccountId) {
		Transaction transaction = Transaction.builder().fromAccountId(fromAccountId).toAccountId(toAccountId).action(TransactionAction.TRANSFER.name())
				.status(TransactionStatus.SUCCESS.name()).createdDate(Instant.now()).updatedDate(Instant.now()).build();
		return transactionRepository.save(transaction);
	}


}
