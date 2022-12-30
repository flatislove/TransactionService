package com.flvtisv.testsolva.controllersGraphql.dto;

import java.math.BigDecimal;

public record TransactionInput(String account_from, String account_to, String currency_shortname, BigDecimal sum,
                               String expense_category) {
}