package com.flvtisv.testsolva.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(schema = "public", name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "rate")
    private BigDecimal rate;
    @Column(name = "date_cur")
    private String date;

    public Currency(String symbol, BigDecimal rate, String date) {
        this.symbol = symbol;
        this.rate = rate;
        this.date = date;
    }
}
