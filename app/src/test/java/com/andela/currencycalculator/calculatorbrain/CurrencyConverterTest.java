package com.andela.currencycalculator.calculatorbrain;

import com.andela.currencycalculator.model.currency.Currency;
import com.andela.currencycalculator.model.helper.MockData;
import com.andela.currencycalculator.model.jsonparser.CurrencyJsonParser;
import com.andela.currencycalculator.model.jsonparser.DbManagerListener;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Spykins on 07/03/2016.
 */
public class CurrencyConverterTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testExchangeFromCurrencyTo() throws Exception {

                CurrencyConverter  currencyConverter = new CurrencyConverter();
                currencyConverter.setAllCurrencyInDb(new MockData().getArrayList());
                CurrencyMode currencyMode = new CurrencyMode(currencyConverter);

                currencyMode.addInputIntoArray("USD : 10");
                currencyMode.onPressedOfFunctionKey("+");
                currencyMode.addInputIntoArray("NGN : 10");
                String answer = currencyMode.getResult("USD");
                assertTrue(answer.equals("10.05"));

                currencyMode.reInitializeArray();

                currencyMode.addInputIntoArray("KWD : 100");
                currencyMode.onPressedOfFunctionKey("+");
                currencyMode.addInputIntoArray("LYD : 200");
                answer = currencyMode.getResult("NGN");

                assertTrue(answer.equals("94706.34"));

                answer = currencyConverter.exchangeFromCurrencyTo("USD", "NGN","1");
                assertTrue(answer.equals("198.51"));

                answer = currencyConverter.exchangeFromCurrencyTo("GHS", "NGN","1");
                assertTrue(answer.equals("51.42"));


    }

}