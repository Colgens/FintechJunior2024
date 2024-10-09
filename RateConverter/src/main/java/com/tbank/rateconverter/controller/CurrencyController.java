package com.tbank.rateconverter.controller;

import com.tbank.rateconverter.model.CurrencyRate;
import com.tbank.rateconverter.model.CurrencyRequest;
import com.tbank.rateconverter.model.CurrencyResponse;
import com.tbank.rateconverter.service.CurrencyService;
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

    @GetMapping("/rates/{code}")
    public ResponseEntity<CurrencyRate> getCurrencyRate(@PathVariable String code) {
        return ResponseEntity.ok(currencyService.getCurrencyRate(code));
    }

    @PostMapping("/convert")
    public ResponseEntity<CurrencyResponse> convertCurrency(@Valid @RequestBody CurrencyRequest request) {
        return ResponseEntity.ok(currencyService.convertCurrency(request));
    }
}