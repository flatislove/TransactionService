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
    @JoinColumn(name = "account_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
    @Column(name = "limit_op")
    private BigDecimal limit;
    @Column(name = "date_set")
    private Date dateLimit;
    @Column(name = "type_limit")
    private String type;

    public Limit(Account account, BigDecimal limit, Date dateLimit, String type) {
        this.account = account;
        this.limit = limit;
        this.dateLimit = dateLimit;
        this.type = type;
    }
}
