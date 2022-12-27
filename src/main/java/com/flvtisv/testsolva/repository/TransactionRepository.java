package com.flvtisv.testsolva.repository;

import com.flvtisv.testsolva.entity.Transaction;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> getTransactionsByAccountIdAndStatusFlagTrue(long accountId);

    //    @Query("select sum(t.sumOfMoney) from Transaction t where Transaction.accountId=:id")
    @Query("select sum(t.sumOfMoney) from Transaction t where t.account.id=:id")
    BigDecimal getSumTransactionsById(@Param("id") long id);

//    @Query("select Account.number, Transaction.currency, Transaction.sumOfMoney, Transaction.type, Transaction.date, Limit.limit, Limit.dateLimit, Transaction.currency as limit_cur from Transaction left join Limit on Transaction.limitId=Limit.id left join Account on Transaction.accountId=Account.ownerId where Transaction.statusFlag=true and Transaction.accountId=:id")
//    JSONObject getJsonObject(@Param("id") long id);

}
