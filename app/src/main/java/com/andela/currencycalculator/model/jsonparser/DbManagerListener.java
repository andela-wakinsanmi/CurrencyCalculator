package com.andela.currencycalculator.model.jsonparser;

import com.andela.currencycalculator.model.currency.Currency;

import java.util.ArrayList;

/**
 * Created by Spykins on 27/02/2016.
 */
public interface DbManagerListener {
    void passDataToDb(ArrayList<Currency> dataFromJson);
}
