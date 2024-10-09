package com.tbank.rateconverter.model;

import lombok.Data;

@Data
public class CurrencyResponse {
    private String fromCurrency;
    private String toCurrency;
    private double convertedAmount;

    public CurrencyResponse(String fromCurrency, String toCurrency, double convertedAmount) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.convertedAmount = convertedAmount;
    }
}