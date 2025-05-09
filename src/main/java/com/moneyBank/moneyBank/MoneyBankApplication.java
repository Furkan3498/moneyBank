package com.moneyBank.moneyBank;

import com.moneyBank.moneyBank.model.Account;
import com.moneyBank.moneyBank.model.City;
import com.moneyBank.moneyBank.model.Currency;
import com.moneyBank.moneyBank.model.Customer;
import com.moneyBank.moneyBank.repository.AccountRepository;
import com.moneyBank.moneyBank.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MoneyBankApplication implements CommandLineRunner {


	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;

	public MoneyBankApplication(AccountRepository accountRepository, CustomerRepository customerRepository) {
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(MoneyBankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Customer c1 = Customer.builder()
				.id("1234")
				.address("Ev")
				.name("Furkan Ceylan")
				.dateOfBirth(1998)
				.city(City.SELANIK)
				.build();
		Customer c2 = Customer.builder()
				.id("3457")
				.address("Sarigazi")
				.name("Hüseyin Elitaş ")
				.dateOfBirth(2000)
				.city(City.MUGLA)
				.build();
		Customer c3 = Customer.builder()
				.id("3437")
				.address("Okul")
				.name("Emirhan Lekesiztaş")
				.dateOfBirth(1997)
				.city(City.KOCAELI)
				.build();
		Customer c4 = Customer.builder()
				.id("3476")
				.address("Bulgaristan")
				.name("Tolga Güneş")
				.dateOfBirth(1995)
				.city(City.IGDIR)
				.build();
		Customer c5 = Customer.builder()
				.id("7777")
				.address("istanbul")
				.name("furkish")
				.dateOfBirth(1995)
				.city(City.IGDIR)
				.build();

		customerRepository.saveAll(Arrays.asList(c1,c2,c3,c4,c5));

		Account a1 = Account.builder()
				.id("100")
				.customerId("1234")
				.city(City.EDIRNE)
				.balance(15000.0)
				.currency(Currency.TRY)
				.build();
		Account a2 = Account.builder()
				.id("102")
				.customerId("3457")
				.city(City.ANKARA)
				.currency(Currency.RIYAL)
				.balance(5000.0)
				.build();
		Account a3 = Account.builder()
				.id("101")
				.customerId("3476")
				.city(City.ANTALYA)
				.currency(Currency.EURO)
				.balance(500.0)
				.build();
		Account a4 = Account.builder()
				.id("103")
				.customerId("3437")
				.currency(Currency.USD)
				.city(City.KOCAELI)
				.balance(10000.0)
				.build();
		Account a5 = Account.builder()
				.id("104")
				.customerId("7777")
				.currency(Currency.TRY)
				.city(City.KOCAELI)
				.balance(10000.0)
				.build();

		accountRepository.saveAll(Arrays.asList(a1,a2,a3,a4,a5));
	}
}
