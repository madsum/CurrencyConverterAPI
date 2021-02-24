package com.example.currencyapp.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This model holds all necessary values for exchange currency
 * @since 24-02-2021
 * */
public class ExchangeCurrencyInfo {

    private static final Logger logger = LogManager.getLogger(ExchangeCurrencyInfo.class);

    private BigDecimal exchangeRate;
    private BigDecimal localAmount;
    private String localCurrencyCode;
    private String exchangeCurrencyCode;
    private BigDecimal exchangedAmount;
    private String result;

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate.setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getLocalAmount() {
        return localAmount;
    }

    public void setLocalAmount(BigDecimal localAmount) {
        this.localAmount = localAmount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getExchangedAmount() {
        return exchangedAmount;
    }

    public void setExchangedAmount(BigDecimal exchangedAmount) {
        this.exchangedAmount = exchangedAmount;
    }

    public String getResult() {
        return result;
    }

    public void populateResult(String result) {
        this.result = result;
    }

    public void setExchangeCurrencyCode(String exchangeCurrencyCode) {
        this.exchangeCurrencyCode = exchangeCurrencyCode;
    }

    public String getLocalCurrencyCode() {
        return localCurrencyCode;
    }

    public void setLocalCurrencyCode(String localCurrencyCode) {
        if(localCurrencyCode.isEmpty()){
            this.localCurrencyCode = "euro";
        }else{
            this.localCurrencyCode = localCurrencyCode;
        }

    }

    public String getExchangeCurrencyCode() {
        return exchangeCurrencyCode;
    }

    /**
     * This will populate result
     * @since 24-02-2021
     */
    public void populateResult(){
        result = "Current exchange rate is: "+exchangeRate+". "
                + localAmount +" "+ localCurrencyCode +" exchanged amount will be "
                + exchangedAmount +" "+exchangeCurrencyCode;
    }
}
