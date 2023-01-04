package com.flvtisv.testsolva.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class AccountAdd {
    Long ownerId;
    String number;
    BigDecimal balance;
}
