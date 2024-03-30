package com.moneyBank.moneyBank.service;


import com.moneyBank.moneyBank.RequestDtos.CreateCustomerRequest;
import com.moneyBank.moneyBank.dto.CustomerDto;
import com.moneyBank.moneyBank.dto.CustomerDtoConverter;
import com.moneyBank.moneyBank.model.City;
import com.moneyBank.moneyBank.model.Customer;
import com.moneyBank.moneyBank.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {


    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter customerDtoConverter) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
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

        //Static ile tanımlarsak böyle yapabiliriz
        //return CustomerDtoConverter.convert(customer);

        return customerDtoConverter.convert(customer);
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer customer: customerList){

            //böyle yapsaydık 2 tane instance yaratıcaktık ve memoryde daha fazla yer tutacaktı.
            //o yüzden diğer türlü yaparsak maliyet azalır
            //CustomerDto customerDto = customerDtoConverter.convert(customer);
            //customerDtoList.add(customerDto);

            customerDtoList.add(customerDtoConverter.convert(customer));
    }
        return  customerDtoList;
    }
}
