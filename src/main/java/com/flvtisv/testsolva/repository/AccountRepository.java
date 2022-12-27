package com.flvtisv.testsolva.repository;

import com.flvtisv.testsolva.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account getAccountByNumber(String number);

    Account getAccountByOwnerId(long ownerId);
}
