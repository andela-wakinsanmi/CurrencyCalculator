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
        calculatorMode.onPressedOfFunctionKey(CalculatorConstant.CALC_MULTIPLY);
        calculatorMode.addInputIntoArray("3");
        calculatorMode.onPressedOfFunctionKey("+");
        calculatorMode.addInputIntoArray("5");
        calculatorMode.onPressedOfFunctionKey("/");
        calculatorMode.addInputIntoArray("6");

        String answer = calculatorMode.getResult("");
        assertTrue(answer.equals("6.83"));

    }

}