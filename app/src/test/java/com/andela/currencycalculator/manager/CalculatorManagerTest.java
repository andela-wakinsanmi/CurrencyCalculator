package com.andela.currencycalculator.manager;

import com.andela.currencycalculator.calculatorbrain.CurrencyConverter;
import com.andela.currencycalculator.manager.CalculatorManager;
import com.andela.currencycalculator.model.helper.MockData;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Spykins on 07/03/2016.
 */
public class CalculatorManagerTest {
    CalculatorManager calculatorManager = new CalculatorManager(new MockData().getArrayList(), false);

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void testAddInputIntoArray() throws Exception {

        CurrencyConverter currencyConverter = new CurrencyConverter();
        currencyConverter.setAllCurrencyInDb(new MockData().getArrayList());

        calculatorManager.addInputIntoArray("20");
        calculatorManager.onPressedOfFunctionKey("+");
        calculatorManager.addInputIntoArray("100");
        String answer = calculatorManager.performOperation("");
        assertTrue(answer.equals("120"));


        calculatorManager.addInputIntoArray("2");
        calculatorManager.onPressedOfFunctionKey("/");
        calculatorManager.addInputIntoArray("4");
        answer = calculatorManager.performOperation("");
        assertTrue(answer.equals("0.5"));


    }

    @Test
    public void testExchangeFromCurrencyTo() throws Exception {
        String answer = calculatorManager.exchangeFromCurrencyTo("USD","NGN", "10");
        assertTrue(answer.equals("1985.12"));
    }

    @Test
    public void testSwitchMode() throws Exception {

    }

}