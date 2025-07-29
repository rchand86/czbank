package com.cz.bank.czbank.controller;

import com.cz.bank.czbank.exception.InvalidInputException;
import com.cz.bank.czbank.model.CreditRating;
import com.cz.bank.czbank.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RatingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingController.class);

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * Retrieves the credit rating details for a customer based on customer ID and rating.
     * @param customerId      the unique identifier of the customer
     * @param customerRating  the rating of the customer that can be "A", "B", "C", or "D"
     * @return a CreditRating of the customer
     */
    @GetMapping("/api/credit-rating")
    public ResponseEntity<CreditRating> getCustomerDetailsByCusIdCusRating(@RequestParam Long customerId, @RequestParam String customerRating) {
        if (customerId == null || customerId <= 0 || !StringUtils.hasText(customerRating)) {
            LOGGER.error("Validation failed : CustomerId/CustomerRating should not be Empty/Blank");
            throw new InvalidInputException("CustomerId/CustomerRating should not be Empty/Blank");
        }
        String cusRating = ratingService.retrieveCustomerRating(customerRating);
        CreditRating creditRating = ratingService.retrieveCustomerRating(customerId, cusRating);
        return ResponseEntity.status(HttpStatus.OK).body(creditRating);
    }

}
