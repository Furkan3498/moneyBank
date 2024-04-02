package com.moneyBank.moneyBank.RequestDtos;

import com.moneyBank.moneyBank.dto.CityDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BaseCustomerRequest {

    private  String name;
    private Integer dateOfBirth;
    private CityDto city;
    private String address;
}
