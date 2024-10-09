package com.tbank.rateconverter.service;

import com.tbank.rateconverter.model.CurrencyRate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyServiceImplTest {

    @Test
    public void testConvertAmount_CorrectConversion() {
        CurrencyRate usdRate = new CurrencyRate("RUB", 1, 1.0, 1.0);
        CurrencyRate eurRate = new CurrencyRate("KZT", 100, 19.9302, 0.199302);

        CurrencyServiceImpl currencyService = new CurrencyServiceImpl(null, null, null);


        double convertedAmount = currencyService.convertAmount(100.0, usdRate, eurRate);


        assertEquals(501.75111137871164, convertedAmount, 0.001);
    }
}