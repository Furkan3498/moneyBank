package com.moneyBank.moneyBank.requestDtos;

import com.moneyBank.moneyBank.model.City;
import com.moneyBank.moneyBank.model.Currency;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BaseAccountRequest {


    @NotBlank(message = "Customer id must not be null")
    private String customerId;

    @NotNull
    @Min(0)
    private Double balance;
    @NotNull(message = "Currency must not be null")
    private Currency currency;
    @NotNull(message = "City must not be null")
    private City city;
}
