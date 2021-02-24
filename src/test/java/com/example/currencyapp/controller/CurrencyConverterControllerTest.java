package com.example.currencyapp.controller;

import com.example.currencyapp.model.ExchangeCurrencyInfo;
import com.example.currencyapp.service.ForeignExchangeRateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CurrencyConverterControllerTest {

    @InjectMocks
    private CurrencyConverterController currencyConverterController;

    @Mock
    private ForeignExchangeRateService foreignExchangeRateService;

    @Test
    void getExCurrencyTest() throws Exception {
        // Arrange
        ExchangeCurrencyInfo exchangeCurrencyInfo = new ExchangeCurrencyInfo();
        exchangeCurrencyInfo.setExchangeRate(new BigDecimal("1.21"));
        exchangeCurrencyInfo.setLocalAmount(new BigDecimal("10"));
        exchangeCurrencyInfo.setExchangedAmount(new BigDecimal("12.14"));
        exchangeCurrencyInfo.populateResult();

        // Mock
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(foreignExchangeRateService.getExchangeCurrencyInfo(any(), any(), any())).thenReturn(exchangeCurrencyInfo);

        // Act
        var expectedResult = currencyConverterController.getExCurrency("eur", "usd", 10);

        // Assert
        Assertions.assertEquals(expectedResult, exchangeCurrencyInfo);
    }

    @Test
    void getAllSupportedCurrencyTest() throws IOException {
        // Arrange
        List<String> expectedCurrencyList = List.of("cad", "usd", "gbp", "sek", "aud");

        // Mock
        when(foreignExchangeRateService.getAllSupportedCurrency("eur")).thenReturn(expectedCurrencyList);

        // Act
        var actualCurrencyList =  currencyConverterController.getAllSupportedCurrency("eur");

        // Assert
        assertLinesMatch(expectedCurrencyList, actualCurrencyList);
    }
}
