package com.moneyBank.moneyBank.controller;

import com.moneyBank.moneyBank.RequestDtos.CreateCustomerRequest;
import com.moneyBank.moneyBank.dto.CustomerDto;
import com.moneyBank.moneyBank.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<String> bismillah(){
        return ResponseEntity.ok("Bismillâhirrahmânirrahîm");
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest){


        return ResponseEntity.ok(customerService.createCustomer(createCustomerRequest));
    }
}
