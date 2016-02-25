package com.andela.currencycalculator.model.currency;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Spykins on 20/02/2016.
 */
public class Currency {
    private String baseCurrency;
    private String currency;
    private double exchangeRate;
    private String dateCreated;


    public Currency(String baseCurrency, double exchangeRate, String currency) {
        this.baseCurrency = baseCurrency;
        this.exchangeRate = exchangeRate;
        this.currency = currency;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateCreated = sdf.format(date);
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDateCreated() {
        return dateCreated;
    }
}
