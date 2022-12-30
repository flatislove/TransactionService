package com.flvtisv.testsolva.controllersGraphql.dto;

import java.math.BigDecimal;

public record TransactionExceeded(Long id, String account_from, String account_to, String currency_shortname,
                                  BigDecimal sum, String expense_category, String datetime, BigDecimal limit_sum,
                                  String limit_datetime, String limit_currency_shortname) {
}
