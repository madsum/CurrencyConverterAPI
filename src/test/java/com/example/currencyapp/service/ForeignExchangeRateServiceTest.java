package com.example.currencyapp.service;

import com.example.currencyapp.model.ExchangeCurrencyInfo;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ForeignExchangeRateServiceTest {

    private static ForeignExchangeRateService foreignExchangeRateService;

    @BeforeAll
    static void init() {
        foreignExchangeRateService = new ForeignExchangeRateService();
    }

    @Test
    void getExchangeCurrencyInfoTest() throws IOException {
        BigDecimal amount = new BigDecimal("10.00");
        ExchangeCurrencyInfo exchangeCurrencyInfo = foreignExchangeRateService
                .getExchangeCurrencyInfo("eur", "gbp", amount);
        assertTrue(exchangeCurrencyInfo.getExchangeRate().compareTo(BigDecimal.ZERO) > 0);
        assertTrue(exchangeCurrencyInfo.getExchangedAmount().compareTo(BigDecimal.ZERO) > 0);
        assertEquals(amount, exchangeCurrencyInfo.getLocalAmount());
    }

    @Test
    void exchangeRateFormServer() {
        JsonObject allRates = new JsonObject();
        BigDecimal usdRate = new BigDecimal("1.6");
        ForeignExchangeRateService mockForeignExchangeRateService = Mockito
                .mock(ForeignExchangeRateService.class);
        when(mockForeignExchangeRateService
                .exchangeRateFormServer(allRates, "usd"))
                .thenReturn(usdRate);
        assertEquals(usdRate, mockForeignExchangeRateService
                .exchangeRateFormServer(allRates, "usd"));
    }

}