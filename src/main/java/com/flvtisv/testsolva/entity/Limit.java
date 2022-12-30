package com.flvtisv.testsolva.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @JsonIgnore
    private Account account;
    @Column(name = "limit_op")
    private BigDecimal limit;
    @Column(name = "date_set")
    private String dateLimit;
    @Column(name = "type_limit")
    private String type;

    public Limit(Account account, BigDecimal limit, String dateLimit, String type) {
        this.account = account;
        this.limit = limit;
        this.dateLimit = dateLimit;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o != null) {
            Hibernate.getClass(this);
            Hibernate.getClass(o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
