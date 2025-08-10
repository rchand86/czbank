package com.cz.bank.czbank.controller;

import com.cz.bank.czbank.exception.InvalidInputException;
import com.cz.bank.czbank.model.CreditRating;
import com.cz.bank.czbank.model.Customer;
import com.cz.bank.czbank.service.RatingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


@ExtendWith(MockitoExtension.class)
class RatingControllerTest {
    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;


    @Test
    void testGetCustomerDetailsByCusIdCusRating1() {
        Long customerId = 1L;
        String customerRating = "A";
        String cusRating = "A";

        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        CreditRating creditRating = new CreditRating();
        creditRating.setCustomer(customer);
        creditRating.setCustomerRating(cusRating);

        Mockito.when(ratingService.retrieveCustomerRating(customerRating)).thenReturn(cusRating);
        Mockito.when(ratingService.retrieveCustomerRating(customerId, cusRating)).thenReturn(creditRating);

        ResponseEntity<CreditRating> response = ratingController.getCustomerDetailsByCusIdCusRating(customerId, customerRating);

        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals(cusRating, response.getBody().getCustomerRating());
        Assertions.assertEquals(customerId, response.getBody().getCustomer().getCustomerId());
    }

    @Test
    void testGetCustomerDetailsByCusIdCusRating_InvalidInput_NegativeId() {
        InvalidInputException exception = Assertions.assertThrows(
                InvalidInputException.class,
                () -> ratingController.getCustomerDetailsByCusIdCusRating(0L, "A")
        );
        Assertions.assertEquals("CustomerId/CustomerRating should not be Empty/Blank", exception.getMessage());
    }

    @Test
    void testGetCustomerDetailsByCusIdCusRating_InvalidInput_BlankRating() {
        InvalidInputException exception = Assertions.assertThrows(
                InvalidInputException.class,
                () -> ratingController.getCustomerDetailsByCusIdCusRating(1L, " ")
        );
        Assertions.assertEquals("CustomerId/CustomerRating should not be Empty/Blank", exception.getMessage());
    }

}

