package com.andela.currencycalculator.calculatorbrain;

/**
 * Created by Spykins on 02/03/2016.
 */
public class CalculatorMode extends CalculatorBrain {

    public CalculatorMode() {
        super();
    }

    /**
     * This method adds input to the array
     * if i have [2,+,+] already
     * The method will remove the last index [2,+]
     * and adds the new number to it [2,+,3]
     * @param numberEntered
     */
    public void addInputIntoArray(String numberEntered) {
        if (arrayList.size() > 0) {
            arrayList.remove(arrayList.size() - 1);
        }
        arrayList.add(numberEntered);

    }

    public String getResult(String outputCurrency) {
        return performOperation().trim() ;
    }
}
