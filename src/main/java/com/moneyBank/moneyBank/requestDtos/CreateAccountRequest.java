package com.moneyBank.moneyBank.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CreateAccountRequest extends BaseAccountRequest{

    private String id;
}
