package com.andela.currencycalculator;

import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.InstrumentationInfo;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Spykins on 07/03/2016.
 */
public class CurrencyMapTest extends AndroidTestCase {
    MockContext mMockContext = new MockContext();
    CurrencyMap currencyMap;
    @Before
    public void setUp() {
        currencyMap = new CurrencyMap(mContext);
    }


    @Test
    public void testGetCurrencyIcon() throws Exception {
        assertTrue("1".equals("1"));

    }

    @Test
    public void testHasCurrencyInfo() throws Exception {

    }

    @Test
    public void testGetCurrencyName() throws Exception {

    }

    @Test
    public void testGetCurrencyCodeCountryAndSymbol() throws Exception {

    }
}