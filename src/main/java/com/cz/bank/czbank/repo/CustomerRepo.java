package com.cz.bank.czbank.repo;

import com.cz.bank.czbank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
