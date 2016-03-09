package com.andela.currencycalculator.manager;

import android.test.AndroidTestCase;

import com.andela.currencycalculator.model.helper.MockData;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Spykins on 08/03/16.
 */
public class ParserDbManagerTest extends AndroidTestCase {

    ParserDbManager parserDbManager;
    public void setUp() throws Exception {
        parserDbManager = ParserDbManager.getInstance(mContext);
    }

    @Test
    public void testAddAllCurrencyDataToDb() throws Exception {
        parserDbManager.passDataToDb(new MockData().getArrayList());
        parserDbManager.addAllCurrencyDataToDb();

        assertTrue(parserDbManager.readAllCurrencyDataFromDb() !=  null);

    }

    @Test
    public void testReadAllCurrencyDataFromDb() throws Exception {
        assertTrue(parserDbManager.readAllCurrencyDataFromDb() != null);
    }

    @Test
    public void testReadAllCurrencyInFile() throws Exception {

        assertTrue(parserDbManager.readAllCurrencyInFile() != null);
    }

    @Test
    public void testGetCurrencyIcon() throws Exception {

        assertTrue(parserDbManager.getCurrencyIcon("NGN").equals("₦"));
        assertTrue(parserDbManager.getCurrencyIcon("USD").equals("$"));
        assertTrue(parserDbManager.getCurrencyIcon("GHS").equals("¢"));
    }

    @Test
    public void testGetCurrencyCountry() throws Exception {

        assertTrue(parserDbManager.getCurrencyCountry("CNY").equals("China"));
        assertTrue(parserDbManager.getCurrencyCountry("GHS").equals("Ghana"));
        assertTrue(parserDbManager.getCurrencyCountry("HKD").equals("Hong Kong"));

    }
}