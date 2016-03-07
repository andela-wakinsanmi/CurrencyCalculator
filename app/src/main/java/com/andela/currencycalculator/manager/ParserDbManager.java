package com.andela.currencycalculator.manager;

import android.content.Context;

import com.andela.currencycalculator.model.currency.Currency;
import com.andela.currencycalculator.model.currency.CurrencyMap;
import com.andela.currencycalculator.model.dbparser.DbHandler;
import com.andela.currencycalculator.model.jsonparser.CurrencyJsonParser;
import com.andela.currencycalculator.model.jsonparser.DbManagerListener;
import com.andela.currencycalculator.model.jsonparser.JsonParserListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Spykins on 20/02/2016.
 */
public class ParserDbManager implements DbManagerListener {
    private CurrencyJsonParser currencyJsonParser;
    private DbHandler dbHandler;
    private JsonParserListener jsonParserListener;
    private static ParserDbManager parserDbManager;
    private ArrayList<Currency> allCurrency;
    private CurrencyMap currencyMap;
    private Context context;

    private ParserDbManager(Context context) {
        this.context = context;
        currencyMap = new CurrencyMap(context);
        //currencyMap.
        currencyJsonParser = new CurrencyJsonParser(this);
        currencyJsonParser.setDbManagerListener(this);
        allCurrency = new ArrayList<>();
        dbHandler = new DbHandler(context, null, null, 1);
    }

    public static ParserDbManager getInstance(Context context) {
        if (parserDbManager == null) {
            parserDbManager = new ParserDbManager(context);
        }

        return parserDbManager;
    }

    public void getDataFromJson() {
        currencyJsonParser.loadRate();
    }

    public void addAllCurrencyDataToDb() {
        if (dbHandler.hasDataBase(context)) {
            for (Currency currency : allCurrency) {
                dbHandler.updateDatabase(currency.getCurrency(), currency.getExchangeRate());
            }
        } else {
            for (Currency currency : allCurrency) {
                dbHandler.insertCurrencyInDatabase(currency);
            }
        }

        if (jsonParserListener != null && allCurrency.size() != 0) {
            jsonParserListener.notifyActivity(allCurrency);
        } else {
            readAllCurrencyDataFromDb();
        }
    }

    public ArrayList<Currency> readAllCurrencyDataFromDb() {
        return dbHandler.readCurrencyFromDatabase();
    }

    public void parseDataFromJson(ArrayList<Currency> allData) {

        allCurrency = allData;
        addAllCurrencyDataToDb();
    }

    public boolean hasDatabase() {
        return dbHandler.hasDataBase(context);
    }

    public HashMap<String, ArrayList<String>> readAllCurrencyInFile() {
        return currencyMap.getCurrencyCodeCountryAndSymbol();
    }

    public String getCurrencyIcon(String currencyCode) {
        return currencyMap.getCurrencyIcon(currencyCode);
    }

    public String getCurrencyCountry(String currencyCode) {
        return currencyMap.getCurrencyName(currencyCode);
    }

    public void setJsonParserListener(JsonParserListener jsonParserListener) {
        this.jsonParserListener = jsonParserListener;
    }

    @Override
    public void passDataToDb(ArrayList<Currency> dataFromJson) {
        parseDataFromJson(dataFromJson);
    }

    @Override
    public void readDataFromDb() {
        jsonParserListener.notifyActivity(readAllCurrencyDataFromDb());
    }
}
