package com.tbank.rateconverter.controller;

import com.tbank.rateconverter.model.CurrencyRate;
import com.tbank.rateconverter.model.CurrencyRequest;
import com.tbank.rateconverter.model.CurrencyResponse;
import com.tbank.rateconverter.model.ErrorResponse;
import com.tbank.rateconverter.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {
    private final CurrencyService currencyService;

    @Autowired
    CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Operation(summary = "Get currency rate by code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Currency rate found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CurrencyRate.class),
                            examples = @ExampleObject(value = "{\"charCode\": \"USD\", \"nominal\": 1, \"value\": 74.56, \"vunitRate\": 1.0}"))}),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\": 400, \"message\": \"Invalid request\", \"details\": \"Invalid currency code: KZ\"}"))}),
            @ApiResponse(responseCode = "404", description = "Currency not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\": 404, \"message\": \"Currency not found\", \"details\": \"Currency not found in the response: KZ\"}"))}),
            @ApiResponse(responseCode = "503", description = "Service unavailable",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\": 503, \"message\": \"Service unavailable\", \"details\": \"Currency service is currently unavailable\"}"))})
    })
    @GetMapping("/rates/{code}")
    public ResponseEntity<CurrencyRate> getCurrencyRate(@PathVariable String code) {
        return ResponseEntity.ok(currencyService.getCurrencyRate(code));
    }

    @Operation(summary = "Convert currency")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Currency converted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CurrencyResponse.class),
                            examples = @ExampleObject(value = "{\"fromCurrency\": \"USD\", \"toCurrency\": \"EUR\"," +
                                    " \"convertedAmount\": 85.23}"))}),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\": 400, \"message\": \"Invalid request\", " +
                                    "\"details\": \"Invalid currency code: KZ\"}"))}),
            @ApiResponse(responseCode = "503", description = "Service unavailable",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"status\": 503, \"message\": \"Service " +
                                    "unavailable\", \"details\": \"Currency service is currently unavailable\"}"))})
    })
    @PostMapping("/convert")
    public ResponseEntity<CurrencyResponse> convertCurrency(@Valid @RequestBody CurrencyRequest request) {
        return ResponseEntity.ok(currencyService.convertCurrency(request));
    }
}