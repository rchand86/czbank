package com.cz.bank.czbank.controller;

import com.cz.bank.czbank.model.CreditRating;
import com.cz.bank.czbank.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RatingController {

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
        CreditRating creditRating = ratingService.retrieveCustomerRating(customerId, customerRating);
        return ResponseEntity.status(HttpStatus.OK).body(creditRating);
    }

}
