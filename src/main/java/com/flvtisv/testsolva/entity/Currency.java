package com.flvtisv.testsolva.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
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
    private Date date;

    public Currency(BigDecimal rate) {
        this.rate = rate;
    }

    public Currency(String symbol, BigDecimal rate, String date) {
        this.symbol = symbol;
        this.rate = rate;
        this.date = getFormatDate(date);
    }

    @SneakyThrows
    public Date getFormatDate(String date) {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }
}
