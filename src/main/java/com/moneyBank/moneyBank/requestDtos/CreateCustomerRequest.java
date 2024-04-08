package com.moneyBank.moneyBank.requestDtos;


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