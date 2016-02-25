package com.andela.currencycalculator.model.helper;

/**
 * Created by Spykins on 22/02/2016.
 */
public class StringManipulator {
    public static String clearApostrophe(String line){
        return line.trim().replace("\"", "");
    }
    public static String replaceSpaceToLower(String line){
        return line.replace(" ", "").toLowerCase();

    }

    public static  String removeLastComma(String line){
        String cleanDouble = StringManipulator.clearApostrophe(line);
        return cleanDouble.substring(0,cleanDouble.length()-1);
    }
}
