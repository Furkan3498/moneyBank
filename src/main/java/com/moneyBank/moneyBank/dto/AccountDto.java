package com.moneyBank.moneyBank.dto;

import com.moneyBank.moneyBank.model.Currency;
import lombok.Builder;

@Builder
public class AccountDto {



    private String id;
    private String customerId;
    private Double balance;
    private Currency currency;

    public AccountDto(String id, String customerId, Double balance, Currency currency) {
        this.id = id;
        this.customerId = customerId;
        this.balance = balance;
        this.currency = currency;
    }

    public AccountDto() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
