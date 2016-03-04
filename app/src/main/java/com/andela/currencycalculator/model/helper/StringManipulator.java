package com.andela.currencycalculator.model.helper;

import com.andela.currencycalculator.calculatorbrain.CalculatorConstant;

import java.text.DecimalFormat;

/**
 * Created by Spykins on 22/02/2016.
 */
public class StringManipulator {
    public static String clearApostrophe(String line) {
        return line.trim().replace("\"", "");
    }

    public static String replaceSpaceToLower(String line) {
        return line.replace(" ", "").toLowerCase();
    }

    public static String removeLastComma(String line) {
        String cleanDouble = StringManipulator.clearApostrophe(line);
        return cleanDouble.substring(0, cleanDouble.length() - 1);
    }

    public static String formatToTwoDecimalPlaces(double answer) {
        DecimalFormat decimalFormat = new DecimalFormat(".##");
        String answerString = String.valueOf(answer);

        if (answerString.contains("-") || answerString.contains("E")) {
            return answerString;
        }
        if (answer % 1 == 0) {
            if (answerString.contains(".")) {
                return answerString.substring(0, answerString.indexOf("."));
            }

        }
        if (answer == 0) {
            return "0";
        } else if (answer < 1) {
            return 0 + String.valueOf(decimalFormat.format(answer));
        }
        return String.valueOf(decimalFormat.format(answer));
    }

    public static boolean isOperator(String lastInputValue) {
        //String lastInputValue = currencyInput.get(currencyInput.size()-1);
        boolean flag = lastInputValue.equals(CalculatorConstant.CALC_ADDITION) ||
                lastInputValue.equals(CalculatorConstant.CALC_SUBTRACT) ||
                lastInputValue.equals(CalculatorConstant.CALC_MULTIPLY)
                || lastInputValue.equals(CalculatorConstant.CALC_DIVIDE);
        return flag;
    }

}
