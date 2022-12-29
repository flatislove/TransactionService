package com.flvtisv.testsolva.repository;

import com.flvtisv.testsolva.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> getAccountByNumber(String number);
}
