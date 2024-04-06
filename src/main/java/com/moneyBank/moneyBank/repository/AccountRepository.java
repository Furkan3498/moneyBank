package com.moneyBank.moneyBank.repository;

import com.moneyBank.moneyBank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
