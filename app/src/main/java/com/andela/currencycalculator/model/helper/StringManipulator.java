package com.andela.currencycalculator.model.helper;

/**
 * Created by Spykins on 22/02/2016.
 */
public class StringManipulator {
    public static String clearApostrophe(String line){
        return line.trim().replace("\"", "");
    }
}
