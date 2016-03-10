package com.andela.currencycalculator.calculatorbrain;

import android.util.Log;

import com.andela.currencycalculator.model.helper.StringManipulator;

import java.util.ArrayList;

/**
 * Created by Spykins on 02/03/2016.
 */

/**
 *  CalculatorBrain keeps track of the calculator ArrayList
 *  <p>
 *      For this class to function properly
 *      The algorithm is. A client has to add a number into the array
 *      Then add an operator and then add another number
 *      For instance, if i want to perform this calculation [2 + 3 * 5]
 *      The first time you add a number [2],
 *      When you add an operator, i will add it twice [2,+,+]
 *      The next time you add another number,
 *      The program will remove the last operator [2,+] and then add the number [2,+,3]
 *      when the client also add the (*) operator, i will have [2, +,3,*,*],
 *      then on press of 5, [2,+,3,*] and then [2,+,3,*,5]
 *  </p>
 *  For this class to function properly
 */
public abstract class CalculatorBrain {
    protected ArrayList<String> arrayList;
    protected CurrencyConverter currencyConverter;

    public CalculatorBrain() {
        this.arrayList = new ArrayList<>();
    }

    /**
     * This method registers a function key in the ArrayList
     * <P>
     *     This method simply checks if the last item in the array is an operator
     *     If it's true.. It replaces it by removing the last two index of the array
     *     and adding the new operator to it
     * </P>
     * @param functionKey
     */

    public void onPressedOfFunctionKey(String functionKey) {

        if (StringManipulator.isOperator(arrayList.get(arrayList.size() - 1))) {
            arrayList.remove(arrayList.size() - 1);
            arrayList.remove(arrayList.size() - 1);
        }

        arrayList.add(functionKey);
        arrayList.add(functionKey);
    }

    /**
     * This methods performs the operation on the Arraylist and returns a value.
     * @return String
     */

    protected String performOperation() {
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
        return StringManipulator.formatToTwoDecimalPlaces(calc).trim();
    }

    private void removeValueAtIndex(int index, double calculatedValue) {
        arrayList.remove(index);
        arrayList.remove(index);
        arrayList.remove(index);
        arrayList.add(index, Double.toString(calculatedValue));
    }

    public void reInitializeArray() {
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
