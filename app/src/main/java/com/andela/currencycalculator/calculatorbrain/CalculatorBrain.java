package com.andela.currencycalculator.calculatorbrain;

import android.util.Log;

import com.andela.currencycalculator.model.helper.StringManipulator;

import java.util.ArrayList;

/**
 * Created by Spykins on 02/03/2016.
 */
public abstract class CalculatorBrain {
    protected ArrayList<String> arrayList;
    protected CurrencyConverter currencyConverter;

    public CalculatorBrain() {
        this.arrayList = new ArrayList<>();
    }

    public void onPressedOfFunctionKey(String functionKey) {

        if (StringManipulator.isOperator(arrayList.get(arrayList.size() - 1))) {
            arrayList.remove(arrayList.size() - 1);
            arrayList.remove(arrayList.size() - 1);
        }

        arrayList.add(functionKey);
        arrayList.add(functionKey);
    }

    public String performOperation() {
        if (StringManipulator.isOperator(arrayList.get(arrayList.size() - 1))) {
            arrayList.remove(arrayList.size() - 1);
            arrayList.remove(arrayList.size() - 1);
        }

        double calc = 0;
        int arraySize = arrayList.size();

        while (arraySize != 1) {

            if (arraySize > 3) {

                if (arrayList.get(3).contains(CalculatorConstant.CALC_MULTIPLY) ||
                        arrayList.get(3).contains(CalculatorConstant.CALC_DIVIDE)) {
                    calc = doArithmetic(arrayList.get(3), arrayList.get(2), arrayList.get(4));
                    removeValueAtIndex(2, calc);
                    arraySize = arrayList.size();

                } else {
                    calc = doArithmetic(arrayList.get(1), arrayList.get(0), arrayList.get(2));
                    removeValueAtIndex(0, calc);
                    arraySize = arrayList.size();

                }
            } else {
                //if size is less than  or eqal to three
                calc = doArithmetic(arrayList.get(1), arrayList.get(0), arrayList.get(2));
                removeValueAtIndex(0, calc);
                arraySize = arrayList.size();

            }
        }
        //Log.d("waleola", "answer = " + StringManipulator.formatToTwoDecimalPlaces(calc));
        return StringManipulator.formatToTwoDecimalPlaces(calc).trim();
    }

    private void removeValueAtIndex(int index, double calculatedValue) {
        arrayList.remove(index);
        arrayList.remove(index);
        arrayList.remove(index);
        arrayList.add(index, Double.toString(calculatedValue));
    }

    protected void reInitializeArray() {
        arrayList = new ArrayList<>();
    }

    private double doArithmetic(String operator, String firstNumber, String secondNumber) {
        switch (operator) {
            case CalculatorConstant.CALC_ADDITION:
                return Double.parseDouble(firstNumber) + Double.parseDouble(secondNumber);
            case CalculatorConstant.CALC_DIVIDE:
                return Double.parseDouble(firstNumber) / Double.parseDouble(secondNumber);
            case CalculatorConstant.CALC_MULTIPLY:
                return Double.parseDouble(firstNumber) * Double.parseDouble(secondNumber);
            case CalculatorConstant.CALC_SUBTRACT:
                return Double.parseDouble(firstNumber) - Double.parseDouble(secondNumber);
            default:
                return 0;
        }
    }

}
