package com.moneyBank.moneyBank.service;

import com.moneyBank.moneyBank.RequestDtos.CreateAccountRequest;
import com.moneyBank.moneyBank.dto.AccountDto;
import com.moneyBank.moneyBank.dto.AccountDtoConverter;
import com.moneyBank.moneyBank.model.Account;
import com.moneyBank.moneyBank.model.City;
import com.moneyBank.moneyBank.model.Currency;
import com.moneyBank.moneyBank.model.Customer;
import com.moneyBank.moneyBank.repository.AccountRepository;
import com.moneyBank.moneyBank.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AccountServiceTest {


    //private ve void metodların testi yapılmaz
    private AccountService accountService;
    private CustomerService customerService;
    private AccountRepository accountRepository;
    private AccountDtoConverter accountDtoConverter;

    @Before
    public void setUp() throws Exception {
        //yalancı classlar oluşturuyoruz. Bunlar class değil bizim istedğimiz şeyleri alalım diye
        //oluşturduğumuz yalancı mocklar
        accountRepository = Mockito.mock(AccountRepository.class);
        customerService = Mockito.mock(CustomerService.class);
        accountDtoConverter = Mockito.mock(AccountDtoConverter.class);

        accountService = new AccountService(accountRepository, customerService, accountDtoConverter);
    }

    //test metotları public ve void değer olmalı
    @Test
    public void whemCreateAccountCalledWithValidRequest_itShouldReturnValidAccountDt() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setId("1234");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setCurrency(Currency.TRY);


        Customer customer = Customer.builder()
                .id("12345")
                .city(City.ISTANBUL)
                .dateOfBirth(1998)
                .name("Furkan Ceylan")
                .address("Adres")
                .build();

        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .city(createAccountRequest.getCity())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(createAccountRequest.getCustomerId())
                .build();

        AccountDto accountDto = AccountDto.builder()
                .id("1234")
                .customerId("12345")
                .balance(100.0)
                .currency(Currency.TRY)
                .build();
        //biz dto da city nesnesini döndürmek istemedik.
        //ilk önce öylesine request data sonra customer data sonra accout data sonrada da accountDto datalar oluşturduk
        //her cıkan datayı test etmeliyiz
        //customer service, reposistoru.save, dtoconverter gibi

        //Test etmelik bir data yaratıyoruz cünkü getCustomerById metoddu customer check ediyor
        //bu customer da verileri böyle olsun diyoruz.

        //Yukarıdaki id aynı olmalı
        //id verip bana customer dönmeli
        Mockito.when(customerService.getCustomerById("12345")).thenReturn(customer);
      // save account verip account dönmeli
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountDtoConverter.convert(account)).thenReturn(accountDto);



        AccountDto result = accountService.createAccount(createAccountRequest);
        Assert.assertEquals(result, accountDto);
        //bu karsılastırma yapıyor normal sonucla testteki sonuc aynı mı diye
        Mockito.verify(customerService).getCustomerById("12345");
        Mockito.verify(accountRepository).save(account);
        Mockito.verify(accountDtoConverter).convert(account);

    }

    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledWithNonExistCustomer_itShouldReturnEmptyAccountDto(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setId("1234");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setCurrency(Currency.TRY);

        Mockito.when(customerService.getCustomerById("12345")).thenReturn(Customer.builder().build());
        AccountDto expectedAccountDto = AccountDto.builder().build();;
        AccountDto result= accountService.createAccount(createAccountRequest);
        Assert.assertEquals(result,expectedAccountDto);
        //createaAccounttaki if maddesini iceliyoruz
        // if (customer.getId().equals("")  || customer.getId() == null){
        //böyleydi customer ici nul olsa bile ilk equals diyemeyiz == null yazmalıyız
        //null olunca aşağıdakiler bos gececek verifyNoInteractions yaparız
        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }
    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledWithCustomerWithOutId_itShouldReturnEmptyAccountDto(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setId("1234");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setCurrency(Currency.TRY);

        Mockito.when(customerService.getCustomerById("12345")).thenReturn(Customer.builder()
                .id(" ")
                .build());


        AccountDto expectedAccountDto = AccountDto.builder().build();;
        AccountDto result= accountService.createAccount(createAccountRequest);
        Assert.assertEquals(result,expectedAccountDto);
        //customer veritabanına yazılmıs ama idsi yok ya da biri db ile oynarken silinmiştir
        //burada bazen name ile arama yaptığımızda ya da @ıd primary key olmadığından silinmiş olabileceğinde
        //boşluk karakterleri .trim yaparak boslukları siliyoruz
        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }

@Test(expected = RuntimeException.class)
//hangi exception fırlatılıcaksa onu yazmak gerekir
    public void whenCreateAccountCalledAndRepositoryThrewException_itShouldThrowException(){
    CreateAccountRequest createAccountRequest = new CreateAccountRequest();
    createAccountRequest.setId("1234");
    createAccountRequest.setBalance(100.0);
    createAccountRequest.setCity(City.ISTANBUL);
    createAccountRequest.setCustomerId("12345");
    createAccountRequest.setCurrency(Currency.TRY);


    Customer customer = Customer.builder()
            .id("12345")
            .city(City.ISTANBUL)
            .dateOfBirth(1998)
            .name("Furkan Ceylan")
            .address("Adres")
            .build();

    Account account = Account.builder()
            .id(createAccountRequest.getId())
            .city(createAccountRequest.getCity())
            .balance(createAccountRequest.getBalance())
            .currency(createAccountRequest.getCurrency())
            .customerId(createAccountRequest.getCustomerId())
            .build();




    Mockito.when(customerService.getCustomerById("12345")).thenReturn(customer);

    Mockito.when(accountRepository.save(account)).thenThrow(new RuntimeException(" exception "));



   accountService.createAccount(createAccountRequest);


    Mockito.verify(customerService).getCustomerById("12345");
    Mockito.verify(accountRepository).save(account);
    //exception fırlatacağı icin aşağısı calısmayacak
    Mockito.verifyNoInteractions(accountDtoConverter);


}

}