package com.moneyBank.moneyBank.service;

import com.moneyBank.moneyBank.exception.CustomerNotFoundException;
import com.moneyBank.moneyBank.requestDtos.CreateAccountRequest;
import com.moneyBank.moneyBank.requestDtos.MoneyTransferRequest;
import com.moneyBank.moneyBank.requestDtos.UpdateAccountRequest;
import com.moneyBank.moneyBank.dto.AccountDto;
import com.moneyBank.moneyBank.dto.AccountDtoConverter;
import com.moneyBank.moneyBank.model.Account;
import com.moneyBank.moneyBank.model.Customer;
import com.moneyBank.moneyBank.repository.AccountRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
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

    private final DirectExchange exchange;
    private final AmqpTemplate rabbitTemplate;
   // private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Value("${sample.rabbitmq.routingKey}")
    String routingKey;

    @Value("${sample.rabbitmq.queue}")
    String queueName;

    public AccountService(AccountRepository accountRepository, CustomerService customerService, AccountDtoConverter accountDtoConverter, DirectExchange exchange, AmqpTemplate rabbitTemplate) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.accountDtoConverter = accountDtoConverter;
        this.exchange = exchange;
        this.rabbitTemplate = rabbitTemplate;
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

        public void  transferMoney(MoneyTransferRequest moneyTransferRequest){
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, moneyTransferRequest);
        }

        @RabbitListener(queues = "${sample.rabbitmq.queue}")
    public void transferMoneyMessage(MoneyTransferRequest moneyTransferRequest) {
        Optional<Account> accountOptional = accountRepository.findById(moneyTransferRequest.getFromId());
        accountOptional.ifPresentOrElse(account -> {
                    if (account.getBalance() > moneyTransferRequest.getAmount()) {
                        account.setBalance(account.getBalance() - moneyTransferRequest.getAmount());
                        accountRepository.save(account);
                        rabbitTemplate.convertAndSend(exchange.getName(), "secondRoute", moneyTransferRequest);
                    } else {
                        System.out.println("Insufficient funds -> accountId: " + moneyTransferRequest.getFromId() + " balance: " + account.getBalance() + " amount: " + moneyTransferRequest.getAmount());
                    }},
                () -> System.out.println("Account not found")
        );
    }
    @RabbitListener(queues = "secondStepQueue")
    public void updateReceiverAccount(MoneyTransferRequest transferRequest) {
        Optional<Account> accountOptional = accountRepository.findById(transferRequest.getToId());
        accountOptional.ifPresentOrElse(account -> {
                    account.setBalance(account.getBalance() + transferRequest.getAmount());
                    accountRepository.save(account);
                    rabbitTemplate.convertAndSend(exchange.getName(), "thirdRoute", transferRequest);
                },
                () -> {
                    System.out.println("Receiver Account not found");
                    Optional<Account> senderAccount = accountRepository.findById(transferRequest.getFromId());
                    senderAccount.ifPresent(sender -> {
                        System.out.println("Money charge back to sender");
                        sender.setBalance(sender.getBalance() + transferRequest.getAmount());
                        accountRepository.save(sender);
                    });

                }
        );
    }
    @RabbitListener(queues = "thirdStepQueue")
    public void finalizeTransfer(MoneyTransferRequest transferRequest) {
        Optional<Account> accountOptional = accountRepository.findById(transferRequest.getFromId());
        accountOptional.ifPresentOrElse(account ->
                {
                    System.out.println("Sender(" + account.getId() +") new account balance: " + account.getBalance());


                }, () -> System.out.println("Account not found")
        );

        Optional<Account> accountToOptional = accountRepository.findById(transferRequest.getToId());
        accountToOptional.ifPresentOrElse(account ->
        {

            System.out.println("Receiver(" + account.getId() +") new account balance: " + account.getBalance());


        },
                () -> System.out.println("Account not found")
        );


    }


}

























