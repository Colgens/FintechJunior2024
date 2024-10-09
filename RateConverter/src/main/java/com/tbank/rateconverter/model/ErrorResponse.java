package com.tbank.rateconverter.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response model for error")
public class ErrorResponse {
    @Schema(example = "400")
    private int status;

    @Schema(example = "Invalid request")
    private String message;

    @Schema(example = "Invalid currency code: KZ")
    private String details;

    public ErrorResponse(int status, String message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }
}