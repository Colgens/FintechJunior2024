package com.tbank.rateconverter.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrencyRequest {

    @NotNull(message = "fromCurrency is required")
    @NotBlank(message = "fromCurrency is required")
    private String fromCurrency;

    @NotNull(message = "toCurrency is required")
    @NotBlank(message = "toCurrency is required")
    private String toCurrency;

    @NotNull(message = "amount is required")
    @Positive(message = "amount must be greater than 0")
    private Double amount;
}