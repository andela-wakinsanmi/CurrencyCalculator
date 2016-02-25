package com.andela.currencycalculator.model.jsonparser;

/**
 * Created by Spykins on 21/02/2016.
 */
public enum JsonParserConfig {

    JSON_URL ("https://openexchangerates.org/api/latest.json?app_id=125d7e1c1f664d0488a4262f599038ae"),
    JSON_RATES_KEY ("\"rates\""),
    JSON_BASE_CURRENCY ("\"base\"");

    JsonParserConfig(String realName){
        this.realName = realName;
    }
    private String realName;

    public String getRealName(){
        return realName;
    }


}
