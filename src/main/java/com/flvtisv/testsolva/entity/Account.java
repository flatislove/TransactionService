package com.flvtisv.testsolva.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Builder
@Table(schema = "public", name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "owner_id")
    private long ownerId;
    @Column(name = "number")
    private String number;
    @Column(name = "balance")
    private BigDecimal balance;

}
