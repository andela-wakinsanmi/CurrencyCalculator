package com.andela.currencycalculator.calculatorbrain;

import com.andela.currencycalculator.model.helper.RoundValue;
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
                (numberEntered.split(CurrencyConstant.CURR_DELIMITER)[1])) {

            String currency = numberEntered.split(CurrencyConstant.CURR_DELIMITER)[0].trim();
            String amountEntered = numberEntered.split(
                    CurrencyConstant.CURR_DELIMITER)[1].trim();

            String answerAfterConversion = currencyConverter.exchangeFromCurrencyTo(currency,
                    CurrencyConstant.BASE_CURR, amountEntered);

            if (arrayList.size() > 0) {
                arrayList.remove(arrayList.size() - 1);
            }
            arrayList.add(answerAfterConversion);
        }

    }

    public String getResult(String outputCurrency) {
        String answer = performOperation();

        String getExchangeRate = currencyConverter.exchangeFromCurrencyTo(CurrencyConstant.BASE_CURR,
        outputCurrency, String.valueOf(1));

        double answ = Double.valueOf(getExchangeRate) * Double.valueOf(answer) ;
        return (String.valueOf(RoundValue.roundValue(answ)));
    }
}
