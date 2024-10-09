package com.tbank.rateconverter.service;

import com.tbank.rateconverter.model.CurrencyRequest;
import com.tbank.rateconverter.model.CurrencyResponse;
import com.tbank.rateconverter.model.CurrencyRate;

public interface CurrencyService {
    CurrencyRate getCurrencyRate(String code);
    CurrencyResponse convertCurrency(CurrencyRequest request);
}