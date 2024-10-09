package com.tbank.rateconverter.service;

import com.tbank.rateconverter.exception.CurrencyNotFoundException;
import com.tbank.rateconverter.exception.InvalidRequestException;
import com.tbank.rateconverter.exception.ServiceUnavailableException;
import com.tbank.rateconverter.model.CurrencyRequest;
import com.tbank.rateconverter.model.CurrencyResponse;
import com.tbank.rateconverter.model.CurrencyRate;
import com.tbank.rateconverter.model.ValCurs;
import com.tbank.rateconverter.parser.XmlParser;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.logging.Logger;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final Logger logger = Logger.getLogger(CurrencyServiceImpl.class.getName());

    private final RestTemplate restTemplate;
    private final ApplicationContext applicationContext;
    private final XmlParser xmlParser;

    @Autowired
    CurrencyServiceImpl(RestTemplate restTemplate, ApplicationContext applicationContext, XmlParser xmlParser) {
        this.restTemplate = restTemplate;
        this.applicationContext = applicationContext;
        this.xmlParser = xmlParser;
    }

    @Override
    @Cacheable(value = "currencyRates", key = "#code")
    @CircuitBreaker(name = "currencyService", fallbackMethod = "fallbackGetCurrencyRate")
    public CurrencyRate getCurrencyRate(String code) {
        if ("RUB".equals(code)) {
            return new CurrencyRate("RUB", 1, 1.0, 1.0);
        }

        if (isInvalidCurrency(code)) {
            throw new InvalidRequestException("Invalid currency code: " + code);
        }

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String url = String.format("https://www.cbr.ru/scripts/XML_daily.asp?date_req=%s", date);

        String xmlResponse = restTemplate.getForObject(url, String.class);

        if (xmlResponse == null) {
            throw new ServiceUnavailableException("Currency service is currently unavailable");
        }

        ValCurs valCurs = xmlParser.parseXml(xmlResponse);
        CurrencyRate currencyRate = xmlParser.findCurrencyRate(valCurs, code);

        if (currencyRate == null) {
            throw new CurrencyNotFoundException("Currency not found in the response: " + code);
        }

        return currencyRate;
    }

    @Override
    @CircuitBreaker(name = "currencyService", fallbackMethod = "fallbackConvertCurrency")
    public CurrencyResponse convertCurrency(CurrencyRequest request) {
        if (request.getAmount() <= 0) {
            throw new InvalidRequestException("Amount must be greater than 0");
        }

        CurrencyService self = applicationContext.getBean(CurrencyService.class);
        CurrencyRate fromRate = self.getCurrencyRate(request.getFromCurrency());
        CurrencyRate toRate = self.getCurrencyRate(request.getToCurrency());

        double convertedAmount = convertAmount(request.getAmount(), fromRate, toRate);

        return new CurrencyResponse(request.getFromCurrency(), request.getToCurrency(), convertedAmount);
    }

    double convertAmount(double amount, CurrencyRate fromRate, CurrencyRate toRate) {
        return amount * (fromRate.getVunitRate() / toRate.getVunitRate());
    }

    private CurrencyRate fallbackGetCurrencyRate(String code, Throwable t) {
        logger.warning("Fallback method called for currency code: " + code);
        logger.warning("Exception: " + t.getMessage());
        throw new ServiceUnavailableException("Currency service is currently unavailable");
    }

    private CurrencyResponse fallbackConvertCurrency(CurrencyRequest request, Throwable t) {
        logger.warning("Fallback method called for currency conversion request: " + request);
        logger.warning("Exception: " + t.getMessage());
        throw new ServiceUnavailableException("Currency service is currently unavailable");
    }

    private boolean isInvalidCurrency(String code) {
        try {
            Currency.getInstance(code);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }
}