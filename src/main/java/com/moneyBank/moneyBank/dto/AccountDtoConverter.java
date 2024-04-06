package com.moneyBank.moneyBank.dto;

import com.moneyBank.moneyBank.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoConverter {

    public AccountDto convert(Account account){
        //AccountDto accountDto = AccountDto.builder()
         return AccountDto.builder()
                .id(account.getId())
                .currency(account.getCurrency())
                .balance(account.getBalance())
                .customerId(account.getCustomerId())
                .build();

       // return accountDto;

        //AccoutDTO 'ya Lombak özelliği olan @Builder annotionını ekledik.
    }
}
