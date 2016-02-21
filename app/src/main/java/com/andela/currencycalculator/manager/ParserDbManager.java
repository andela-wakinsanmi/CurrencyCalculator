package com.andela.currencycalculator.manager;

import android.content.Context;

import com.andela.currencycalculator.model.currency.Currency;
import com.andela.currencycalculator.model.dbparser.DbHandler;
import com.andela.currencycalculator.model.jsonparser.CurrencyJsonParser;

import java.util.ArrayList;

/**
 * Created by Spykins on 20/02/2016.
 */
public class ParserDbManager {
    private CurrencyJsonParser currencyJsonParser;
    private DbHandler dbHandler;
    private static ParserDbManager parserDbManager;
    private ArrayList<Currency> allCurrency;

    private ParserDbManager(Context context) {
        currencyJsonParser = new CurrencyJsonParser(this);
        allCurrency = new ArrayList<>();
        dbHandler = new DbHandler(context,null,null,1);
        getDataFromJson();
        //addAllCurrencyDataToDb();
    }

    public static ParserDbManager getInstance(Context context){
        if(parserDbManager == null){
            parserDbManager = new ParserDbManager(context);
            return parserDbManager;
        }

        return parserDbManager;

    }

    private void getDataFromJson(){
        currencyJsonParser.loadRate();
    }

    public void addAllCurrencyDataToDb(){
        for(Currency currency : allCurrency){
            dbHandler.insertCurrencyInDatabase(currency);
        }
    }

    public ArrayList<Currency> readAllCurrencyDataFromDb(){
        return dbHandler.readCurrencyFromDatabase();
    }

    public void parseDataFromJson(ArrayList<Currency> allData) {
        allCurrency = allData;
        addAllCurrencyDataToDb();
    }
}
