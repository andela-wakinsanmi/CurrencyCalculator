package com.andela.currencycalculator.calculatorbrain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Spykins on 29/02/2016.
 */
public enum CalculatorConstant {
    CALC_MULTIPLY("*"),
    CALC_ADDITION("+"),
    CALC_DIVIDE("/"),
    CALC_SUBTRACT("-");

    private static final Map<String, CalculatorConstant> map = buildMap();

    private static Map<String, CalculatorConstant> buildMap() {
        Map<String, CalculatorConstant> map = new HashMap<>();
        for (CalculatorConstant e : values()) {
            map.put(e.getRealName(), e);
        }
        return map;
    }

    public static CalculatorConstant getByRealName(String realName) {
        return map.get(realName);
    }


    private CalculatorConstant(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    private final String realName;

}
