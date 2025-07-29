package com.cz.bank.czbank.model;

public enum CreditRatingEnum {
    A("A", 6.0),
    B("B", 6.5),
    C("C", 8.0),
    D("D", 10.5);

    private final String code;
    private final double interestRate;

    CreditRatingEnum(String code, double interestRate) {
        this.code = code;
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public static CreditRatingEnum from(String input) {
        for (CreditRatingEnum rating : values()) {
            if (rating.code.equalsIgnoreCase(input)) {
                return rating;
            }
        }
        return null;
    }
}

