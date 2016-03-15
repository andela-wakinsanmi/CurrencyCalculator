package com.andela.currencycalculator.calculatorbrain;

/**
 * Created by Spykins on 02/03/2016.
 */
public enum  CurrencyConstant {
    BASE_CURR ("USD"),
    CURR_DELIMITER (":");

    private CurrencyConstant(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }
}
