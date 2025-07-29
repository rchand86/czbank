package com.cz.bank.czbank.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CreditRating")
/*@Getter
@Setter*/
public class CreditRating {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(String customerRating) {
        this.customerRating = customerRating;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String customerRating;

    @OneToOne
    @JoinColumn(name = "customerId", referencedColumnName = "customerId", nullable = false, unique = true)
    private Customer customer;

    @Column
    private Double interestRate;

    // Constructors
    public CreditRating(String customerRating, Customer customer, Double interestRate) {
        this.customerRating = customerRating;
        this.customer = customer;
        this.interestRate = interestRate;
    }

    public CreditRating() {
    }

}

