package com.tbank.rateconverter.controller;

import com.tbank.rateconverter.exception.CurrencyNotFoundException;
import com.tbank.rateconverter.exception.ServiceUnavailableException;
import com.tbank.rateconverter.model.CurrencyRate;
import com.tbank.rateconverter.model.CurrencyRequest;
import com.tbank.rateconverter.model.CurrencyResponse;
import com.tbank.rateconverter.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @Test
    public void testConvertCurrency() throws Exception {
        CurrencyResponse response = new CurrencyResponse("USD", "EUR", 85.0);

        when(currencyService.convertCurrency(any(CurrencyRequest.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromCurrency\":\"USD\",\"toCurrency\":\"EUR\",\"amount\":100.0}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fromCurrency").value("USD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.toCurrency").value("EUR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.convertedAmount").value(85.0));
    }

    @Test
    public void testConvertCurrencyInvalidRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromCurrency\":\"USD\",\"amount\":100.0}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetCurrencyRate() throws Exception {
        String currencyCode = "USD";
        CurrencyRate currencyRate = new CurrencyRate("USD", 1, 70.0, 70.0);

        when(currencyService.getCurrencyRate(currencyCode)).thenReturn(currencyRate);

        mockMvc.perform(MockMvcRequestBuilders.get("/currencies/rates/{code}", currencyCode))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.charCode").value(currencyRate.getCharCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nominal").value(currencyRate.getNominal()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(currencyRate.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vunitRate").value(currencyRate.getVunitRate()));
    }

    @Test
    public void testGetCurrencyRate_NotFound() throws Exception {
        String currencyCode = "INVALID";

        when(currencyService.getCurrencyRate(currencyCode))
                .thenThrow(new CurrencyNotFoundException("Currency not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/currencies/rates/{code}", currencyCode))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testConvertCurrency_EmptyFromCurrency() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromCurrency\":\"\",\"toCurrency\":\"EUR\",\"amount\":100.0}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fromCurrency")
                        .value("fromCurrency is required"));
    }

    @Test
    public void testConvertCurrency_MissingFromCurrency() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"toCurrency\":\"EUR\",\"amount\":100.0}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fromCurrency")
                        .value("fromCurrency is required"));
    }

    @Test
    public void testConvertCurrency_EmptyToCurrency() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromCurrency\":\"USD\",\"toCurrency\":\"\",\"amount\":100.0}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.toCurrency")
                        .value("toCurrency is required"));
    }

    @Test
    public void testConvertCurrency_MissingToCurrency() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromCurrency\":\"USD\",\"amount\":100.0}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.toCurrency")
                        .value("toCurrency is required"));
    }

    @Test
    public void testConvertCurrency_EmptyAmount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromCurrency\":\"USD\",\"toCurrency\":\"EUR\",\"amount\":\"\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount")
                        .value("amount is required"));
    }

    @Test
    public void testConvertCurrency_MissingAmount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromCurrency\":\"USD\",\"toCurrency\":\"EUR\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount")
                        .value("amount is required"));
    }

    @Test
    public void testConvertCurrency_NegativeAmount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromCurrency\":\"USD\",\"toCurrency\":\"EUR\",\"amount\":\"-10\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount")
                        .value("amount must be greater than 0"));
    }

    @Test
    public void testConvertCurrency_ValidationResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromCurrency\":\"RUB\",\"amount\":2}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.toCurrency")
                        .value("toCurrency is required"));
    }

    @Test
    public void testConvertCurrency_ServiceUnavailable() throws Exception {
        when(currencyService.convertCurrency(any(CurrencyRequest.class)))
                .thenThrow(new ServiceUnavailableException("Service unavailable"));

        mockMvc.perform(MockMvcRequestBuilders.post("/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromCurrency\":\"USD\",\"toCurrency\":\"EUR\",\"amount\":100.0}"))
                .andExpect(MockMvcResultMatchers.status().isServiceUnavailable());
    }

    @Test
    public void testGetCurrencyRate_ServiceUnavailable() throws Exception {
        String currencyCode = "USD";

        when(currencyService.getCurrencyRate(currencyCode))
                .thenThrow(new ServiceUnavailableException("Service unavailable"));

        mockMvc.perform(MockMvcRequestBuilders.get("/currencies/rates/{code}", currencyCode))
                .andExpect(MockMvcResultMatchers.status().isServiceUnavailable());
    }
}

