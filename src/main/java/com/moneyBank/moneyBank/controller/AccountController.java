package com.moneyBank.moneyBank.controller;

import com.moneyBank.moneyBank.RequestDtos.CreateAccountRequest;
import com.moneyBank.moneyBank.RequestDtos.UpdateAccountRequest;
import com.moneyBank.moneyBank.dto.AccountDto;
import com.moneyBank.moneyBank.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccount(){
        return ResponseEntity.ok(accountService.getAllAccount());
    }
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable String id){
        return ResponseEntity.ok(accountService.getAccountById(id));
    }
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody CreateAccountRequest createAccountRequest){
        return ResponseEntity.ok(accountService.createAccount(createAccountRequest));
    }
    @PutMapping
    public ResponseEntity<AccountDto> updateAcount(@PathVariable String id, @RequestBody UpdateAccountRequest updateAccountRequest){
        return ResponseEntity.ok(accountService.updateAccount(id,updateAccountRequest));
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAccount(@PathVariable String id){
       accountService.deleteAccpunt(id);
       return ResponseEntity.ok().build();
    }

    @PutMapping("/withdraw/{id}/{amount}")
    public ResponseEntity<AccountDto> withdrawMoney(@PathVariable String id, @PathVariable Double amount){
        return ResponseEntity.ok(accountService.withdrawMoney(id,amount));
    }
    @PutMapping("/add/{id}/{amount}")
    public ResponseEntity<AccountDto> addMoney(@PathVariable String id, @PathVariable Double amount){
        return ResponseEntity.ok(accountService.addMoney(id,amount));
    }
}




























