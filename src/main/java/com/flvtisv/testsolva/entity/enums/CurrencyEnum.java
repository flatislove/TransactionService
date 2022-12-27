package com.flvtisv.testsolva.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CurrencyEnum {
    KZT("KZT"),
    RUB("RUB"),
    USD("USD");

    private final String name;
}