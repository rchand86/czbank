package com.cz.bank.czbank.repo;

import com.cz.bank.czbank.model.CreditRating;
import com.cz.bank.czbank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditRatingRepo extends JpaRepository<CreditRating, Long> {

    Optional<CreditRating> findByCustomer(Customer customer);
}
