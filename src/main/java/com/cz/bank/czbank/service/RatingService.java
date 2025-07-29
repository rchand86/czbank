package com.cz.bank.czbank.service;

import com.cz.bank.czbank.exception.InvalidInputException;
import com.cz.bank.czbank.model.CreditRating;
import com.cz.bank.czbank.model.CreditRatingEnum;
import com.cz.bank.czbank.model.Customer;
import com.cz.bank.czbank.repo.CreditRatingRepo;
import com.cz.bank.czbank.repo.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class RatingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingService.class);

    private final CustomerRepo customerRepo;
    private final CreditRatingRepo creditRatingRepo;

    public RatingService(CustomerRepo customerRepo, CreditRatingRepo creditRatingRepo) {
        this.customerRepo = customerRepo;
        this.creditRatingRepo = creditRatingRepo;
    }

    /**
     * Retrieves the CreditRating for a customer based on their ID and rating.
     * It contains the Interest Rate based on the credit rating.
     * @param customerId      the ID of the customer
     * @param customerRating  the credit rating of the customer
     * @return CreditRating object containing the customer's credit rating and interest rate
     * @throws InvalidInputException if customerId or customerRating is empty or blank
     */
    public CreditRating retrieveCustomerRating(Long customerId, String customerRating) {
        Customer customer = retrieveCustomer(customerId,customerRating);
        CreditRating creditRating;
        if(customer != null){
            //Assume Third party service and get the interest rate based on the customer rating
            double interestRate = getInterestRate(customerRating);
            LOGGER.info("Interest Rate for customer {} Interest Rate is {}", customer.getCustomerId(), interestRate);
            creditRating = saveCreditRating(customerRating, customer, interestRate);
        }else{
            LOGGER.error("Validation failed : Customer not exist in DB");
            throw new InvalidInputException("Customer not exist in DB");
        }
        return creditRating;
    }

    /**
     * Returns the interest rate based on the credit rating from static Data.
     * @param rating the Customer rating
     * @return the interest rate corresponding to the credit rating
     * To-DO: We can call a third-party service to get the interest rate based on the credit rating.
     */
    public double getInterestRate(String rating) {
        CreditRatingEnum creditRating = CreditRatingEnum.from(rating);
        return creditRating != null ? creditRating.getInterestRate() : 13.0;
    }

    public String retrieveCustomerRating(String rating) {
        Map<String,String> ratingmap = getInterestRateMap();
        return ratingmap.getOrDefault(rating.toUpperCase(), "Unknown Rating");
    }

    private CreditRating saveCreditRating(String rating, Customer customer, double interestRate) {
        CreditRating creditRating;
        creditRating = creditRatingRepo.findByCustomer(customer).orElse(null);
        if(creditRating != null && StringUtils.hasLength(creditRating.getCustomerRating())) {
            LOGGER.info("Credit Rating already exists for customer: {}", customer.getCustomerId());
        }else{
            creditRating = new CreditRating(rating, customer,interestRate);
            creditRating = creditRatingRepo.save(creditRating);
        }
        return creditRating;
    }

    private Customer retrieveCustomer(Long customerId, String customerRating) {
        Optional<Customer> customer = customerRepo.findById(customerId);
        if(!StringUtils.hasLength(customerRating) || customer.isEmpty()){
            LOGGER.error("No customer found with ID: {} or CustomerRating is empty", customerId);
            return null;
        }
        return customer.get();
    }

    private Map<String, String> getInterestRateMap() {
        HashMap<String,String> ratingMap = new HashMap<>();
        ratingMap.put("EXCELLENT", "A");
        ratingMap.put("GOOD", "B");
        ratingMap.put("AVERAGE", "C");
        ratingMap.put("POOR", "D");
        return ratingMap;
    }
}
