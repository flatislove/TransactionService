package com.flvtisv.testsolva.controllersGraphql.dto;

import java.math.BigDecimal;

public record TransactionView(Long id, String account_to, String expense_category, String datetime, Boolean limit_exceeded,
                              Long limit_id, String currency_shortname, BigDecimal sum) {
}

