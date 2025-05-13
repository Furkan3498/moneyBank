package com.moneyBank.moneyBank.repository;

import com.moneyBank.moneyBank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Currency;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account,String> {

    //select * from account where balance >$(balance)
    //List<Account> findAllByBalanceGreaterThan(Double balance);



}
