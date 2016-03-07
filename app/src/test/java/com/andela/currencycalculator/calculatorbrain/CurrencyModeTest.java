package com.andela.currencycalculator.calculatorbrain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Spykins on 06/03/2016.
 */
public class CurrencyModeTest {
    CurrencyMode currencyMode  = new CurrencyMode(new CurrencyConverter());

    @Before
    public void setUp() throws Exception {
        //currencyMode = new CurrencyMode(new CurrencyConverter());

    }

    @Test
    public void testAddInputIntoArray() throws Exception {
        currencyMode.addInputIntoArray("USD : 10");
        currencyMode.onPressedOfFunctionKey("+");
        currencyMode.addInputIntoArray("USD : 10");
        currencyMode.performOperation();

        assertTrue(currencyMode.getResult("USD").equals("20"));

    }

    @Test
    public void testGetResult() throws Exception {

    }

    @Test
    public void testOnPressedOfFunctionKey() throws Exception {

    }

}