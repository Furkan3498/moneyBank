package com.moneyBank.moneyBank.RequestDtos;

import com.moneyBank.moneyBank.dto.CityDto;

public class CreateCustomerRequest {
    private  String id;
    private  String name;
    private Integer dateOfBirth;
    private CityDto city;
    private String address;

    public CreateCustomerRequest() {
    }

    public CreateCustomerRequest(String id, String name, Integer dateOfBirth, CityDto city, String address) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Integer dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public CityDto getCity() {
        return city;
    }

    public void setCity(CityDto city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
