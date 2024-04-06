package com.moneyBank.moneyBank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    private String id;
    private String customerId;
    private Double balance;
    private City city;
    private Currency currency;
}
