package com.moneyBank.moneyBank.requestDtos;

import com.moneyBank.moneyBank.model.City;
import com.moneyBank.moneyBank.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BaseAccountRequest {


    private String customerId;
    private Double balance;
    private Currency currency;
    private City city;
}
