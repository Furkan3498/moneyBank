package com.moneyBank.moneyBank.RequestDtos;

import com.moneyBank.moneyBank.dto.CityDto;
import lombok.Getter;
import lombok.Setter;




public class CreateCustomerRequest extends BaseCustomerRequest  {
    private String id;

    public CreateCustomerRequest(String id) {
        this.id = id;
    }

    public CreateCustomerRequest() {


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}