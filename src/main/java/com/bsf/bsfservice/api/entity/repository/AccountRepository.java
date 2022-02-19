package com.bsf.bsfservice.api.entity.repository;

import com.bsf.bsfservice.api.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

}
