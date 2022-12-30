package com.flvtisv.testsolva.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(schema = "public", name = "accounts")
@Schema(description = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "ID account")
    private long id;
    @Column(name = "owner_id")
    @Schema(description = "account holder ID")
    private long ownerId;
    @Column(name = "number")
    @Schema(description = "number accounts")
    private String number;
    @Column(name = "balance")
    @Schema(description = "current balance")
    private BigDecimal balance;
    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @Schema(description = "account limits")
    private List<Limit> limits = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @Schema(description = "account transactions")
    private List<Transaction> transactions = new ArrayList<>();

    public Account(long ownerId, String number, BigDecimal balance) {
        this.ownerId = ownerId;
        this.number = number;
        this.balance = balance;
    }
}
