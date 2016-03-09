package com.andela.currencycalculator;

import android.test.AndroidTestCase;

import com.andela.currencycalculator.model.currency.CurrencyMap;


/**
 * Created by Spykins on 07/03/2016.
 */
public class CurrencyMapTest extends AndroidTestCase {
    CurrencyMap currencyMap;

    public void setUp() {
        currencyMap = new CurrencyMap(mContext);
    }

    public void testGetCurrencyIcon() throws Exception {
        assertTrue(currencyMap.getCurrencyIcon("NGN").equals("₦"));
        assertTrue(currencyMap.getCurrencyIcon("USD").equals("$"));
        assertTrue(currencyMap.getCurrencyIcon("GHS").equals("¢"));
        assertTrue(currencyMap.getCurrencyIcon("MNT").equals("₮"));
        assertTrue(currencyMap.getCurrencyIcon("SYP").equals( "£"));
    }

    public void testGetCurrencyName() throws Exception {
        assertTrue(currencyMap.getCurrencyName("NGN").equals("Nigeria"));
        assertTrue(currencyMap.getCurrencyName("CHF").equals("Switzerland"));
        assertTrue(currencyMap.getCurrencyName("CNY").equals("China"));
        assertTrue(currencyMap.getCurrencyName("GHS").equals("Ghana"));
        assertTrue(currencyMap.getCurrencyName("HKD").equals("Hong Kong"));
    }

    public void testGetCurrencyCodeCountryAndSymbol() throws Exception {
        assertTrue(currencyMap.getCurrencyCodeCountryAndSymbol() != null);

    }
}