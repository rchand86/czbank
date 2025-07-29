package com.cz.bank.czbank;

import com.cz.bank.czbank.exception.InvalidInputException;
import com.cz.bank.czbank.model.CreditRating;
import com.cz.bank.czbank.model.CreditRatingEnum;
import com.cz.bank.czbank.model.Customer;
import com.cz.bank.czbank.repo.CreditRatingRepo;
import com.cz.bank.czbank.repo.CustomerRepo;
import com.cz.bank.czbank.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingServiceTest {

    private CustomerRepo customerRepo;
    private CreditRatingRepo creditRatingRepo;
    private RatingService ratingService;

    @BeforeEach
    void setUp() {
        customerRepo = mock(CustomerRepo.class);
        creditRatingRepo = mock(CreditRatingRepo.class);
        ratingService = new RatingService(customerRepo, creditRatingRepo);
    }

    @Test
    void testRetrieveCustomerRating_Success() {
        Long customerId = 1L;
        String customerRating = "A";
        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        when(customerRepo.findById(customerId)).thenReturn(Optional.of(customer));
        when(creditRatingRepo.findByCustomer(customer)).thenReturn(Optional.empty());
        when(creditRatingRepo.save(any(CreditRating.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CreditRating result = ratingService.retrieveCustomerRating(customerId, customerRating);

        assertNotNull(result);
        assertEquals(customerRating, result.getCustomerRating());
        assertEquals(customer, result.getCustomer());
    }

    @Test
    void testRetrieveCustomerRating_InvalidInput() {
        Long customerId = 1L;
        String customerRating = "";

        when(customerRepo.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(InvalidInputException.class, () ->
                ratingService.retrieveCustomerRating(customerId, customerRating)
        );
    }

    @Test
    void testGetInterestRate_KnownRating() {
        String rating = "A";
        double expectedRate = CreditRatingEnum.from(rating).getInterestRate();

        double rate = ratingService.getInterestRate(rating);

        assertEquals(expectedRate, rate);
    }

    @Test
    void testGetInterestRate_UnknownRating() {
        String rating = "UNKNOWN";

        double rate = ratingService.getInterestRate(rating);

        assertEquals(13.0, rate);
    }
}
