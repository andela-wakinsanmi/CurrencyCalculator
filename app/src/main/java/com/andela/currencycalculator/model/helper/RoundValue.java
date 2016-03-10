package com.andela.currencycalculator.model.helper;

/**
 * Created by Spykins on 10/03/16.
 */
public class RoundValue {

    public static double roundValue(double amount) {
        amount = amount * 100;
        long tmp = Math.round(amount);
        return ((double) tmp/100);
    }
}
