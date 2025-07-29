package com.cz.bank.czbank;

import com.cz.bank.czbank.controller.RatingController;
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
    void testGetCustomerDetailsByCusIdCusRating() {
        // Arrange
        Long customerId = 1L;
        String customerRating = "A";

        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setEmail("test@example.com");
        customer.setMobileNumber("1234567890");

        CreditRating creditRating = new CreditRating();
        creditRating.setCustomer(customer);
        creditRating.setCustomerRating(customerRating);
        creditRating.setInterestRate(7.5);

        Mockito.when(ratingService.retrieveCustomerRating(customerId, customerRating)).thenReturn(creditRating);

        // Act
        ResponseEntity<CreditRating> response = ratingController.getCustomerDetailsByCusIdCusRating(customerId, customerRating);

        // Assert
        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals(response.getBody().getCustomerRating(), customerRating);
    }
}
