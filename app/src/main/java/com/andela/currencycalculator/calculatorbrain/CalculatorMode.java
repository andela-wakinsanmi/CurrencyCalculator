package com.andela.currencycalculator.calculatorbrain;

import android.util.Log;

/**
 * Created by Spykins on 02/03/2016.
 */
public class CalculatorMode extends CalculatorBrain {

    public CalculatorMode() {
        super();
    }

    public void addInputIntoArray(String numberEntered) {
        if (arrayList.size() > 0) {
            arrayList.remove(arrayList.size() - 1);
        }
        arrayList.add(numberEntered);

    }

    public String getResult(String outputCurrency) {
        String answer = performOperation();
        //Log.d("spykins", "Answer from comput = " + answer + " and the length is " + answer.equals("5"));
        return answer.trim() ;
    }
}
