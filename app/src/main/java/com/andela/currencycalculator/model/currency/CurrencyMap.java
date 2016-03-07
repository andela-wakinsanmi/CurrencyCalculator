package com.andela.currencycalculator.model.currency;

import android.content.Context;
import android.util.Log;

import com.andela.currencycalculator.R;
import com.andela.currencycalculator.model.helper.StringManipulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Spykins on 22/02/2016.
 */
public class CurrencyMap {
    private Context context;
    private HashMap<String, String> currencyCodeToSymbol;
    private HashMap<String,String> currencyCodeToCountry;
    private HashMap<String, ArrayList<String>> currencyCodeCountryAndSymbol;


    public CurrencyMap(Context context) {
        currencyCodeToSymbol = new HashMap<>();
        currencyCodeToCountry = new HashMap<>();
        this.context = context;
        fetchCurrencySymbols();

    }

    private void fetchCurrencySymbols() {
        String data = "";
        InputStream inputStream = context.getResources().openRawResource(R.raw.currency);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        currencyCodeCountryAndSymbol = new HashMap<>();
        ArrayList<String> countryAndSymbol;
        try {
            while ((data = reader.readLine()) != null) {
                countryAndSymbol = new ArrayList<>();
                if(data.split(":").length == 3){
                    String code = StringManipulator.clearApostrophe(data.split(":")[0].trim());
                    String country = StringManipulator.clearApostrophe(data.split(":")[1].trim());
                    String symbol = StringManipulator.clearApostrophe(data.split(":")[2].trim());
                    currencyCodeToSymbol.put(code,symbol);
                    currencyCodeToCountry.put(code, country);
                    countryAndSymbol.add(country);
                    countryAndSymbol.add(symbol);
                    currencyCodeCountryAndSymbol.put(code, countryAndSymbol);
                }
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCurrencyIcon(String currencyCode) {
        if (hasCurrencyInfo(currencyCode)) {
            return currencyCodeToSymbol.get(currencyCode);
        }
        return currencyCode;
    }

    public boolean hasCurrencyInfo(String currencyCode){
        return currencyCodeToCountry.containsKey(currencyCode);
    }

    public String getCurrencyName(String currencyCode){
        if(hasCurrencyInfo(currencyCode)){
            return currencyCodeToCountry.get(currencyCode);
        }
        return null;
    }

    public HashMap<String, ArrayList<String>> getCurrencyCodeCountryAndSymbol() {
            return currencyCodeCountryAndSymbol;
    }
}
