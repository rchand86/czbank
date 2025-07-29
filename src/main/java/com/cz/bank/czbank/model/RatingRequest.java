package com.cz.bank.czbank.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class RatingRequest {

    @NotBlank(message = "Customer-Rating is required")
    private String customerRating;

    @NotBlank(message = "Customer-Id is required")
    private Long customerId;

}
