package com.moneyBank.moneyBank.service;


import com.moneyBank.moneyBank.RequestDtos.CreateCustomerRequest;
import com.moneyBank.moneyBank.dto.CityDto;
import com.moneyBank.moneyBank.dto.CustomerDto;
import com.moneyBank.moneyBank.model.City;
import com.moneyBank.moneyBank.model.Customer;
import com.moneyBank.moneyBank.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {


    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDto createCustomer(CreateCustomerRequest createCustomerRequest){

        Customer customer = new  Customer();
        customer.setAddress(createCustomerRequest.getAddress());
        customer.setName(createCustomerRequest.getName());
        customer.setDateOfBirth(createCustomerRequest.getDateOfBirth());
        customer.setId(createCustomerRequest.getId());
        customer.setCity(City.valueOf(createCustomerRequest.getCity().name()));


        //Eğer biz id yi Integer ya da Long olarak tanımlasaydık ve @GenaratedValue deseydik
        //Id enttiy tarafından kendisi verilerek tanımlancaktı.
        //customerRepository.save(customer) ' ı Customer nesnesi yaratarak döndürecektik.
        //Biz String tipinde kendimiz girerek veriyoruz Id'yi
        //Ama şu an ıd değerini biz verdiğimiz için geri döndürüken de girdiğimiz şeyleri alaacğız.
        customerRepository.save(customer);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAddress(customer.getAddress());
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setDateOfBirth(customer.getDateOfBirth());
        customerDto.setCity(CityDto.valueOf(customer.getCity().name()));
        return customerDto;
    }
}
