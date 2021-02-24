package com.example.currencyapp.controller;

import com.example.currencyapp.model.ExchangeCurrencyInfo;
import com.example.currencyapp.service.ForeignExchangeRateService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


/**
 * Controller to get currency exchanged currency amount
 *
 * @since 24-02-2021
 */
@RestController
@RequestMapping(path = CurrencyConverterController.REQUEST_URL)
public class CurrencyConverterController {

    public static final String REQUEST_URL = "/api/v1/revisions/";
    public static final String GET_EXCHANGE_REQUEST = "exchange-currency";
    public static final String GET_ALL_SUPPORTED_CURRENCY = "all-supported-currency";

    private static final Logger logger = LogManager.getLogger(CurrencyConverterController.class);

    @Autowired
    private ForeignExchangeRateService foreignExchangeRateService;

    /**
     * This will send exchanged currency amount in response with HTTP status code 200.
     *
     * @param localCurrency
     * @param exchangeCurrency
     * @param amount
     * @return
     * @since 24-02-2021
     */
    @ApiOperation(value = "Returns ExchangeCurrencyInfo")
    @GetMapping(value = GET_EXCHANGE_REQUEST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExchangeCurrencyInfo getExCurrency(
            @ApiParam(value = "Local currency 3 character alphabetic currency codes (ISO 4217), i.e. eur, usd, gpb")
            @RequestParam(value = "localCurrency", required = true) String localCurrency,
            @ApiParam(value = "Exchanged currency 3 character alphabetic currency codes (ISO 4217), i.e. eur, usd, gpb")
            @RequestParam(value = "exchangeCurrency", required = true) String exchangeCurrency,
            @ApiParam(value = "A desired amount of money for exchange. A whole number i.e. 10 or floating number 10.50")
            @RequestParam(required = true) double amount) {
        logger.info("amount: " + amount + " currency: " + localCurrency + " exchangeCurrency: " + exchangeCurrency);
        BigDecimal bigDecimalAmount = new BigDecimal(amount);
        ExchangeCurrencyInfo exchangeCurrencyInfo;
        try {
            exchangeCurrencyInfo = foreignExchangeRateService.getExchangeCurrencyInfo(localCurrency, exchangeCurrency, bigDecimalAmount);
        } catch (IOException ex){
            exchangeCurrencyInfo = new ExchangeCurrencyInfo();
            exchangeCurrencyInfo.populateResult("The requested local currency is not supported in our system");
            return exchangeCurrencyInfo;
        }
        if(exchangeCurrencyInfo == null){
            exchangeCurrencyInfo = new ExchangeCurrencyInfo();
            exchangeCurrencyInfo.populateResult("The requested exchange currency is not found in our system");
            return exchangeCurrencyInfo;
        }
        return exchangeCurrencyInfo;
    }

    @ApiOperation(value = "Returns a list of all supported currency for the given currency")
    @GetMapping(value = GET_ALL_SUPPORTED_CURRENCY, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllSupportedCurrency(
            @ApiParam(value = "Local currency 3 character alphabetic currency codes (ISO 4217), i.e. eur, usd, gpb")
            @RequestParam(value = "baseCurrency", required = true) String baseCurrency){
         try {
            return foreignExchangeRateService.getAllSupportedCurrency(baseCurrency);
         } catch (IOException ex){
            return List.of("Not supported currency type");
         }
    }
}
