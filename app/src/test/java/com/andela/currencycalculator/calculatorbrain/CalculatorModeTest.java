package com.andela.currencycalculator.calculatorbrain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Spykins on 04/03/2016.
 */
public class CalculatorModeTest {
    CalculatorMode calculatorMode = new CalculatorMode();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testAddInputIntoArray() throws Exception {

        calculatorMode.addInputIntoArray("2");
        calculatorMode.onPressedOfFunctionKey(CalculatorConstant.CALC_ADDITION);
        calculatorMode.addInputIntoArray("3");
       /* calculatorMode.onPressedOfFunctionKey("+");
        calculatorMode.addInputIntoArray("3");
        calculatorMode.onPressedOfFunctionKey("/");
        calculatorMode.addInputIntoArray("6");*/

        calculatorMode.performOperation();
        assertTrue(Integer.parseInt(calculatorMode.getResult("")) == 5);




    }

    @Test
    public void testGetResult() throws Exception {

    }
}