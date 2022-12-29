package com.flvtisv.testsolva.repository;

import com.flvtisv.testsolva.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Iterable<Transaction> getTransactionsByAccountIdAndStatusFlagTrue(long accountId);

    @Query("select sum(t.sumOfMoney) from Transaction t where t.account.id=:id")
    BigDecimal getSumTransactionsById(@Param("id") long id);

}
