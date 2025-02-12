package com.moneyBank.moneyBank.service;

import com.moneyBank.moneyBank.exception.CustomerNotFoundException;
import com.moneyBank.moneyBank.requestDtos.CreateAccountRequest;
import com.moneyBank.moneyBank.requestDtos.UpdateAccountRequest;
import com.moneyBank.moneyBank.dto.AccountDto;
import com.moneyBank.moneyBank.dto.AccountDtoConverter;
import com.moneyBank.moneyBank.model.Account;
import com.moneyBank.moneyBank.model.Customer;
import com.moneyBank.moneyBank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter accountDtoConverter;


   // private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    public AccountService(AccountRepository accountRepository, CustomerService customerService, AccountDtoConverter accountDtoConverter) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.accountDtoConverter = accountDtoConverter;
    }

    public AccountDto createAccount(CreateAccountRequest createAccountRequest){
        Customer customer = customerService.getCustomerById(createAccountRequest.getCustomerId());
        if (customer.getId() == null || customer.getId().trim().equals("")  ){
            throw new CustomerNotFoundException("Customer Not Found");
        }
        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .city(createAccountRequest.getCity())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(createAccountRequest.getCustomerId())
                .build();
        return accountDtoConverter.convert(accountRepository.save(account));
    }
    public AccountDto updateAccount(String id , UpdateAccountRequest updateAccountRequest){
        Customer customer = customerService.getCustomerById(updateAccountRequest.getCustomerId());
        if (customer.getId().equals("") || customer.getId() ==null){
            return AccountDto.builder().build();
        }
        Optional<Account> accountOptional =  accountRepository.findById(id);
        accountOptional.ifPresent(account ->{
            account.setCity(updateAccountRequest.getCity());
            account.setCustomerId(updateAccountRequest.getCustomerId());
            account.setCurrency(updateAccountRequest.getCurrency());
            account.setBalance(updateAccountRequest.getBalance());
            accountRepository.save(account);
        });
        // return accountOptional.map(account -> accountDtoConverter.convert(account)).orElse(new AccountDto());
        return accountOptional.map(accountDtoConverter ::convert).orElse(new AccountDto());
    }

    public List<AccountDto> getAllAccount(){
        List<Account> accountList = accountRepository.findAll();
        //List<AccountDto> accountDtoList = new ArrayList<>();

        //for (Account account : accountList){
        //     AccountDto accountDto = new AccountDto();
        //    accountDto.setId(accountDto.getId());
        //
        //  1. secenek--> accountDtoList.add(accountDto);
         // 2. secenek accountDtoList.add(accountDtoConverter.convert(account));
        // }
        //return accountDtoList;
        return accountList.stream().map(accountDtoConverter::convert).collect(Collectors.toList());
    }

    public AccountDto getAccountById(String id){
        return accountRepository.findById(id).map(accountDtoConverter::convert).orElse(new AccountDto());
    }
    public void deleteAccpunt(String id){
        accountRepository.deleteById(id);
    }

    public AccountDto withdrawMoney(String id, Double amount){
        Optional<Account> accountOptional = accountRepository.findById(id);
        accountOptional.ifPresent(account -> {
            if (account.getBalance() > amount){
                account.setBalance(account.getBalance() - amount);
                accountRepository.save(account);
            }else {
                System.out.println("Insufficient Funds -> accountId" + id + " balanca " + account.getBalance() + " amount" + amount);
            }
        });
        return accountOptional.map(accountDtoConverter :: convert).orElse(new AccountDto());
    }

    public AccountDto addMoney(String id, Double amount){
        Optional<Account> accountOptional = accountRepository.findById(id);
        accountOptional.ifPresent(account -> {

                account.setBalance(account.getBalance() + amount);
                accountRepository.save(account);

        });
        return accountOptional.map(accountDtoConverter :: convert).orElse(new AccountDto());
    }
}

























