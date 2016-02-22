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
    private JsonParserInterface jsonParserInterface;
    private static ParserDbManager parserDbManager;
    private ArrayList<Currency> allCurrency;
    Context context;

    private ParserDbManager(Context context) {
        this.context = context;
        currencyJsonParser = new CurrencyJsonParser(this);
        jsonParserInterface = (JsonParserInterface) context;
        allCurrency = new ArrayList<>();
        dbHandler = new DbHandler(context, null, null, 1);
    }

    public static ParserDbManager getInstance(Context context) {
        if (parserDbManager == null) {
            parserDbManager = new ParserDbManager(context);
            return parserDbManager;
        }

        return parserDbManager;

    }

    public void getDataFromJson() {
        currencyJsonParser.loadRate();
    }

    public void addAllCurrencyDataToDb() {
        for (Currency currency : allCurrency) {
            dbHandler.insertCurrencyInDatabase(currency);
        }
        jsonParserInterface.notifyActivity();

    }

    public ArrayList<Currency> readAllCurrencyDataFromDb() {
        return dbHandler.readCurrencyFromDatabase();
    }

    public void parseDataFromJson(ArrayList<Currency> allData) {

        allCurrency = allData;
        addAllCurrencyDataToDb();
    }

    public interface JsonParserInterface {
        void notifyActivity();
    }

    public boolean hasDatabase(){
        return dbHandler.hasDataBase(context);
    }
}
