package com.andela.currencycalculator.model.currency;

import android.content.Context;

import com.andela.currencycalculator.R;
import com.andela.currencycalculator.model.helper.StringManipulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by Spykins on 22/02/2016.
 */
public class CurrencyMap {
    private Context context;
    private HashMap<String, String> currencySymbols;

    public CurrencyMap(Context context) {
        currencySymbols = new HashMap<>();
        this.context = context;
        fetchCurrencySymbols();
    }

    private void fetchCurrencySymbols() {
        String data = "";
        InputStream inputStream = context.getResources().openRawResource(R.raw.currency);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while ((data = reader.readLine()) != null) {
                currencySymbols.put(StringManipulator.clearApostrophe(data.split(":")[0].trim()),
                        StringManipulator.clearApostrophe(data.split(":")[1].trim()));
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCurrencyIcon(String currencySymbol) {
        if (currencySymbol.contains(currencySymbol)) {
            return currencySymbols.get(currencySymbol);
        }
        return currencySymbol;
    }


}
