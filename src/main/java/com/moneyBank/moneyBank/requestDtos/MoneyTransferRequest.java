package com.moneyBank.moneyBank.requestDtos;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class MoneyTransferRequest {

    private String fromId;
    private String toId;
    private Double amount;
}
