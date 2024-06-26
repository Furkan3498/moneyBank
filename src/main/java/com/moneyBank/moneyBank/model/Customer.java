package com.moneyBank.moneyBank.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "customer")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    private String id;
    @Column(name = "customer-name")
    private String name;
    private Integer dateOfBirth;
    private City city;
    private String address;


}
