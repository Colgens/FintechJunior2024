package com.tbank.rateconverter.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Response model for currency rate")
public class CurrencyRate {
    @Schema(example = "USD")
    private String charCode;

    @Schema(example = "1")
    private int nominal;

    @Schema(example = "74.56")
    private double value;

    @Schema(example = "1.0")
    private double vunitRate;
}