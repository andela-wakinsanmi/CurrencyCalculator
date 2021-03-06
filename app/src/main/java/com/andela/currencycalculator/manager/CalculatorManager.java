package com.andela.currencycalculator.manager;

import com.andela.currencycalculator.calculatorbrain.CalculatorBrain;
import com.andela.currencycalculator.calculatorbrain.CalculatorMode;
import com.andela.currencycalculator.calculatorbrain.CurrencyConverter;
import com.andela.currencycalculator.calculatorbrain.CurrencyMode;
import com.andela.currencycalculator.model.currency.Currency;

import java.util.ArrayList;

/**
 * Created by Spykins on 26/02/2016.
 */
public class CalculatorManager {

    private CurrencyConverter currencyConverter;
    private CalculatorBrain calculatorFunction;
    private boolean isCurrencyMode;


    public CalculatorManager(ArrayList<Currency> currencies, boolean isCurrencyMode) {
        this.isCurrencyMode = isCurrencyMode;
        currencyConverter = new CurrencyConverter();
        switchMode(isCurrencyMode);
        currencyConverter.setAllCurrencyInDb(currencies);
    }

    public String exchangeFromCurrencyTo(String convertFrom, String convertTo, String amount) {
        return currencyConverter.exchangeFromCurrencyTo(convertFrom, convertTo, amount);
    }

    /**
        This checks the state of the UI when calling the addInputArray
        i.e Calculator or Currency Mode...
     */
    public void addValueToArray(String inputNumber) {
        if (isCurrencyMode) {
            ((CurrencyMode) calculatorFunction).addInputIntoArray(inputNumber);
        } else {
            ((CalculatorMode) calculatorFunction).addInputIntoArray(inputNumber);

        }
    }

    public void reInitializeArray() {
        calculatorFunction.reInitializeArray();
    }

    public String performOperation(String outputCurrency) {
        if (isCurrencyMode) {
            return ((CurrencyMode) calculatorFunction).getResult(outputCurrency);
        }
        return ((CalculatorMode) calculatorFunction).getResult();

    }

    public void onPressedOfFunctionKey(String inputNumber) {
        calculatorFunction.onPressedOfFunctionKey(inputNumber);
    }

    public void switchMode(Boolean isCurrencyMode) {
        this.isCurrencyMode = isCurrencyMode;
        if (isCurrencyMode) {
            calculatorFunction = new CurrencyMode(currencyConverter);
        } else {
            calculatorFunction = new CalculatorMode();
        }
    }

}
