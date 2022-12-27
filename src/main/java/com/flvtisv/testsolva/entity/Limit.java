package com.flvtisv.testsolva.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Builder
@Table(schema = "public", name = "limits")
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "account_id")
    private long accountId;
    @Column(name = "limit_op")
    private BigDecimal limit;
    @Column(name = "date_set")
    private Date dateLimit;
    @Column(name = "type_limit")
    private String type;

    public Limit(long accountId, BigDecimal limit, Date dateLimit, String type) {
        this.accountId = accountId;
        this.limit = limit;
        this.dateLimit = dateLimit;
        this.type = type;
    }
}
