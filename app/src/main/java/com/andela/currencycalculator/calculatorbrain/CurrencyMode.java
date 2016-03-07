package com.andela.currencycalculator.calculatorbrain;

import android.util.Log;

import com.andela.currencycalculator.model.helper.StringManipulator;

/**
 * Created by Spykins on 02/03/2016.
 */
public class CurrencyMode extends CalculatorBrain {

    public CurrencyMode(CurrencyConverter currencyConverter) {
        super();
        this.currencyConverter = currencyConverter;
    }

    public void addInputIntoArray(String numberEntered) {

        if (!numberEntered.equals("") && !StringManipulator.isOperator
                (numberEntered.split(CurrencyConstant.CURRENCY_DELIMETER)[1])) {
            String currency = numberEntered.split(CurrencyConstant.CURRENCY_DELIMETER)[0].trim();
            String amountEntered = numberEntered.split(
                    CurrencyConstant.CURRENCY_DELIMETER)[1].trim();

            String answerAfterConversion = currencyConverter.exchangeFromCurrencyTo(currency,
                    CurrencyConstant.BASE_CURRENCY, amountEntered);

            if (arrayList.size() > 0) {
                arrayList.remove(arrayList.size() - 1);
            }
            arrayList.add(answerAfterConversion);
        }

    }

    public String getResult(String outputCurrency) {
        String answer = performOperation();

        return currencyConverter.exchangeFromCurrencyTo(CurrencyConstant.BASE_CURRENCY,
                outputCurrency, answer);
    }
}
