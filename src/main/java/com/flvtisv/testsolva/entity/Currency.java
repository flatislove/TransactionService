package com.flvtisv.testsolva.entity;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "currency ID")
    private long id;
    @Column(name = "symbol")
    @Schema(description = "currency code ISO 4217")
    private String symbol;
    @Schema(description = "current rate")
    @Column(name = "rate")
    private BigDecimal rate;
    @Schema(description = "rate date")
    @Column(name = "date_cur")
    private String date;

    public Currency(String symbol, BigDecimal rate, String date) {
        this.symbol = symbol;
        this.rate = rate;
        this.date = date;
    }
}
