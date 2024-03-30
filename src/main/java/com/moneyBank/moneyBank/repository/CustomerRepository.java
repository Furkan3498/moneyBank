package com.moneyBank.moneyBank.repository;

import com.moneyBank.moneyBank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer , String> {
}
