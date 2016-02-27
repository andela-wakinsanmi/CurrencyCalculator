package com.andela.currencycalculator.calculatorbrain;

import com.andela.currencycalculator.model.currency.Currency;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Spykins on 26/02/2016.
 */
public class CurrencyConverter{

    HashMap<String, Double>currencyAndExchangeRate;

    public  CurrencyConverter(){
        currencyAndExchangeRate = new HashMap<>();
    }

    public String exchangeFromCurrencyTo(String convertFrom, String convertTo, String amount){

        double answer = 0;
        DecimalFormat decimalFormat = new DecimalFormat(".##");
        if(currencyAndExchangeRate.containsKey(convertFrom) &&
                currencyAndExchangeRate.containsKey(convertTo)){
            answer = performCalculation(convertFrom,convertTo,amount);
        }
        if(answer == 0){
            return "0";
        }else if(answer < 1){
           return  0 + String.valueOf(decimalFormat.format(answer));
        }

        return String.valueOf(decimalFormat.format(answer));
    }

    private double performCalculation(String convertFrom, String convertTo, String amount){

        double answer;
        if(amount.equals("0") || amount.equals("")){
            return 0;
        }
        double inputExchangeRateToDollar = currencyAndExchangeRate.get(convertFrom);
        double amountConverting = Double.parseDouble(amount);
        double inputInDollar = amountConverting/inputExchangeRateToDollar;

        double outputExchangeRateToDollar = currencyAndExchangeRate.get(convertTo);
        answer = inputInDollar * outputExchangeRateToDollar;
        return answer;
    }

    public void setAllCurrencyInDb(ArrayList<Currency> currencies){
        for (Currency currency : currencies){
            currencyAndExchangeRate.put(currency.getCurrency(),currency.getExchangeRate());
        }
    }

}
