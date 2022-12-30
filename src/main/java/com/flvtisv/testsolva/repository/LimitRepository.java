package com.flvtisv.testsolva.repository;

import com.flvtisv.testsolva.entity.Limit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LimitRepository extends CrudRepository<Limit, Long> {

    @Query("select l from Limit as l where l.account.id=:id and l.type=:type order by l.id DESC limit 1")
    Optional<Limit> getLimitByAccountIdAndType(@Param("id") long accountId, @Param("type") String type);

    Limit getLimitById(long id);

    List<Limit> getAllByAccount_Number(String accountNumber);
}
