package com.andela.currencycalculator.model.dbparser;

/**
 * Created by Spykins on 21/02/2016.
 */

public enum DbConfig {
    DATABASE_NAME ("exchangeRate.db"),
    TABLE_NAME ("currency_exchange"),
    COLUMN_ID ("_id"),
    COLUMN_BASE_CURRENCY ("baseCurrency"),
    COLUMN_CURRENCY("currency"),
    COLUMN_EXCHANGE_RATE("exchangeRate"),
    COLUMN_DATE ("dateCreated");

    private DbConfig(String realName){
        this.realName = realName;
    }

    public String getRealName(){
        return realName;
    }
    private final String realName;


}

