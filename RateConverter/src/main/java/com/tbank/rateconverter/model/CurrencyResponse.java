package com.tbank.rateconverter.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response model for currency conversion")
public class CurrencyResponse {
    @Schema(example = "USD")
    private String fromCurrency;

    @Schema(example = "EUR")
    private String toCurrency;

    @Schema(example = "85.23")
    private double convertedAmount;

    public CurrencyResponse(String fromCurrency, String toCurrency, double convertedAmount) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.convertedAmount = convertedAmount;
    }
}