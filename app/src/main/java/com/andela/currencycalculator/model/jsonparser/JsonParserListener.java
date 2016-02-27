package com.andela.currencycalculator.model.jsonparser;

import com.andela.currencycalculator.model.currency.Currency;

import java.util.ArrayList;

/**
 * Created by Spykins on 25/02/2016.
 */
public interface JsonParserListener {
    void notifyActivity( ArrayList<Currency> currencies);
}
