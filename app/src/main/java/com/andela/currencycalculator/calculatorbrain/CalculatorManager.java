package com.andela.currencycalculator.calculatorbrain;

import android.content.Context;

import com.andela.currencycalculator.model.currency.Currency;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by Spykins on 26/02/2016.
 */
public class CalculatorManager {

    private ArrayList<String> currencyInput;
    CurrencyConverter currencyConverter;

    public CalculatorManager(ArrayList<Currency> currencies){
        currencyConverter = new CurrencyConverter();
        currencyConverter.setAllCurrencyInDb(currencies);
        currencyInput = new ArrayList<>();
    }

    public String exchangeFromCurrencyTo(String convertFrom, String convertTo, String amount){
        return currencyConverter.exchangeFromCurrencyTo(convertFrom,convertTo,amount);
    }

    public boolean isOperator(){
        String lastInputValue = currencyInput.get(currencyInput.size()-1);
        boolean flag = lastInputValue == "+" || lastInputValue == "-" || lastInputValue == "X"
                || lastInputValue == "/";
        return flag;
    }




}
