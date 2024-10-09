package com.tbank.rateconverter.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Request model for currency conversion")
public class CurrencyRequest {

    @NotNull(message = "fromCurrency is required")
    @NotBlank(message = "fromCurrency is required")
    @Schema(example = "USD")
    private String fromCurrency;

    @NotNull(message = "toCurrency is required")
    @NotBlank(message = "toCurrency is required")
    @Schema(example = "EUR")
    private String toCurrency;

    @NotNull(message = "amount is required")
    @Positive(message = "amount must be greater than 0")
    @Schema(example = "100.0")
    private Double amount;
}