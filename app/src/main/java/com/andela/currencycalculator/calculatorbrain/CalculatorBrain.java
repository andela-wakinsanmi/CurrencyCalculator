package com.andela.currencycalculator.calculatorbrain;

import com.andela.currencycalculator.model.helper.StringManipulator;

import java.util.ArrayList;

/**
 * Created by Spykins on 02/03/2016.
 */
public abstract class CalculatorBrain {
    protected ArrayList<String> arrayList;
    protected CurrencyConverter currencyConverter;


    /*
            Steps.... create array list
            String string1 and string 2
            when function key is pressed

            1. Add the input Text To the ArrayList
            2. enter the symbol two times ...
            3. add it to the textview
            4. ****clear the string value as soon as operator is entered
            5.... when operator is clicked.... call addInputIntoArray() and onPressedOfFunctionKey

     */

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

    protected String performOperationNow() {
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

                    if (arrayList.get(3).contains(CalculatorConstant.CALC_MULTIPLY)) {
                        calc = Double.parseDouble(arrayList.get(2)) * Double.parseDouble(arrayList.get(4));
                    }
                    if (arrayList.get(3).contains(CalculatorConstant.CALC_DIVIDE)) {
                        calc = Double.parseDouble(arrayList.get(2)) / Double.parseDouble(arrayList.get(4));
                    }
                    removeValueAtIndex(2, calc);
                    arraySize = arrayList.size();

                } else {
                    if (arrayList.get(1).contains(CalculatorConstant.CALC_ADDITION)) {
                        calc = Double.parseDouble(arrayList.get(0)) + Double.parseDouble(arrayList.get(2));
                    }
                    if (arrayList.get(1).contains(CalculatorConstant.CALC_SUBTRACT)) {
                        calc = Double.parseDouble(arrayList.get(0)) - Double.parseDouble(arrayList.get(2));
                    }
                    if (arrayList.get(1).contains(CalculatorConstant.CALC_MULTIPLY)) {
                        calc = Double.parseDouble(arrayList.get(0)) * Double.parseDouble(arrayList.get(2));
                    }
                    if (arrayList.get(1).contains(CalculatorConstant.CALC_DIVIDE)) {
                        calc = Double.parseDouble(arrayList.get(0)) / Double.parseDouble(arrayList.get(2));
                    }

                    removeValueAtIndex(0, calc);
                    arraySize = arrayList.size();

                }
            } else {
                //if size is less than  or eqal to three
                if (arrayList.get(1).contains(CalculatorConstant.CALC_ADDITION)) {
                    calc = Double.parseDouble(arrayList.get(0)) + Double.parseDouble(arrayList.get(2));
                }

                if (arrayList.get(1).contains(CalculatorConstant.CALC_SUBTRACT)) {
                    calc = Double.parseDouble(arrayList.get(0)) - Double.parseDouble(arrayList.get(2));
                }

                if (arrayList.get(1).contains(CalculatorConstant.CALC_MULTIPLY)) {
                    calc = Double.parseDouble(arrayList.get(0)) * Double.parseDouble(arrayList.get(2));
                }

                if (arrayList.get(1).contains(CalculatorConstant.CALC_DIVIDE)) {
                    calc = Double.parseDouble(arrayList.get(0)) / Double.parseDouble(arrayList.get(2));
                }

                removeValueAtIndex(0, calc);
                arraySize = arrayList.size();

            }

        }

        return StringManipulator.formatToTwoDecimalPlaces(calc);
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

}
