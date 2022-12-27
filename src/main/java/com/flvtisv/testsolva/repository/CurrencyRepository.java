package com.flvtisv.testsolva.repository;

import com.flvtisv.testsolva.entity.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    Currency getFirstBySymbolOrderByDate(String symbol);
}
