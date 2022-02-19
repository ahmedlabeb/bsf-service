package com.bsf.bsfservice.api.entity.repository;

import com.bsf.bsfservice.api.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Long> {
}
