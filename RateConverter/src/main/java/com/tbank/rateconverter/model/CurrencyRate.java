package com.tbank.rateconverter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CurrencyRate {
    private String charCode;
    private int nominal;
    private double value;
    private double vunitRate;
}