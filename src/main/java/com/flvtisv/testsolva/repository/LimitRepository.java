package com.flvtisv.testsolva.repository;

import com.flvtisv.testsolva.entity.Limit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitRepository extends CrudRepository<Limit, Long> {

//    @Query("select l from Limit l where l.accountId=:id and l.type=:type")

    @Query("select l from Limit as l where l.accountId=:id and l.type=:type order by l.id DESC limit 1")
    Limit getLimitByAccountIdAndType(@Param("id") long accountId, @Param("type") String type);

    Limit getLimitById(long id);
}


