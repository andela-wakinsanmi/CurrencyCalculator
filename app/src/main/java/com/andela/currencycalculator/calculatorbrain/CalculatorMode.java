package com.andela.currencycalculator.calculatorbrain;

/**
 * Created by Spykins on 02/03/2016.
 */
public class CalculatorMode extends CalculatorBrain {

    public CalculatorMode() {
        super();
    }

    protected void addInputIntoArray(String numberEntered) {
        if (arrayList.size() > 0) {
            arrayList.remove(arrayList.size() - 1);
        }
        arrayList.add(numberEntered);

    }

    protected String getResult(String outputCurrency) {
        return performOperationNow();
    }
}
