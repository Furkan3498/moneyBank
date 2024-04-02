package com.moneyBank.moneyBank.service;


import com.moneyBank.moneyBank.RequestDtos.CreateCustomerRequest;
import com.moneyBank.moneyBank.RequestDtos.UpdateCustomerRequest;
import com.moneyBank.moneyBank.dto.CustomerDto;
import com.moneyBank.moneyBank.dto.CustomerDtoConverter;
import com.moneyBank.moneyBank.model.City;
import com.moneyBank.moneyBank.model.Customer;
import com.moneyBank.moneyBank.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {


    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter customerDtoConverter) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
    }

    public CustomerDto createCustomer(CreateCustomerRequest createCustomerRequest) {

        Customer customer = new Customer();
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
        for (Customer customer : customerList) {

            //böyle yapsaydık 2 tane instance yaratıcaktık ve memoryde daha fazla yer tutacaktı.
            //o yüzden diğer türlü yaparsak maliyet azalır
            //CustomerDto customerDto = customerDtoConverter.convert(customer);
            //customerDtoList.add(customerDto);

            customerDtoList.add(customerDtoConverter.convert(customer));
        }
        return customerDtoList;
    }

    public CustomerDto getCustomerById(String id) {

        Optional<Customer> customerOptional = customerRepository.findById(id);
        //Bu kullanım da vardır ama sakıncalıdır
        //Çünkü optianal veri doluda olabilir boşta demektir.
        //Aşağıdaki veri dolu olunca hata alacaktır o yüzden bu kullanım sakıncalıdır.
        //Customer customer = customerOptional.get();
        //En iyi yöntem maplemektir
        //Aşağıdaki gibi de kullanılabilir.
        //return    customerOptional.map(customer ->  customerDtoConverter.convert(customer)).orElse(new CustomerDto());
        return customerOptional.map(customerDtoConverter::convert).orElse(new CustomerDto());

        //Optianal içerisindeki değeri dışarı cıkartmak için mapleme işlemini kullanıyoruz.
    }

    public void deleteCustomer(String id) {

        customerRepository.deleteById(id);
    }

    public CustomerDto updateCustomer(String id, UpdateCustomerRequest customerRequest) {

        Optional<Customer> customerOptional = customerRepository.findById(id);

        customerOptional.ifPresent(customer -> {
            customer.setName(customerRequest.getName());
            customer.setCity(City.valueOf(customerRequest.getCity().name()));
            customer.setAddress(customerRequest.getAddress());
            customer.setDateOfBirth(customerRequest.getDateOfBirth());
        });
        return customerOptional.map(customer -> customerDtoConverter.convert(customer)).orElse(new CustomerDto());

        // Customer customer =new Customer();
        //if (customerOptional.isPresent()){
        //  customer.setName(customerRequest.getName());
        //customer.setAddress(customerRequest.getAddress());
        //customer.setCity(City.valueOf(customerRequest.getCity().name()));
        //customer.setDateOfBirth(customerRequest.getDateOfBirth());
        //}

        //return customerDtoConverter.convert(customer);
    }
}
























