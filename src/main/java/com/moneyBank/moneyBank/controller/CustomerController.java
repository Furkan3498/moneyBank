package com.moneyBank.moneyBank.controller;

import com.moneyBank.moneyBank.RequestDtos.CreateCustomerRequest;
import com.moneyBank.moneyBank.RequestDtos.UpdateCustomerRequest;
import com.moneyBank.moneyBank.dto.CustomerDto;
import com.moneyBank.moneyBank.model.Customer;
import com.moneyBank.moneyBank.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }



    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest){


        return ResponseEntity.ok(customerService.createCustomer(createCustomerRequest));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
       return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable String id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id){
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable String id,
                                                      @RequestBody UpdateCustomerRequest customerRequest){
        return ResponseEntity.ok(customerService.updateCustomer(id,customerRequest));
    }
}
