package com.andela.currencycalculator.model.jsonparser;


import android.net.Uri;
import android.os.AsyncTask;

import com.andela.currencycalculator.model.currency.Currency;
import com.andela.currencycalculator.model.helper.StringManipulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Spykins on 18/02/2016.
 */
public class CurrencyJsonParser {
    private ArrayList<Currency> allDataFromJson;
    private String baseCurrency;
    private DbManagerListener dbManagerListener;
    private BufferedReader reader = null;

    public void setDbManagerListener(DbManagerListener dbManagerListener) {
        this.dbManagerListener = dbManagerListener;
    }

    public CurrencyJsonParser(DbManagerListener dbManagerListener) {
        this.dbManagerListener = dbManagerListener;
    }

    public void loadRate() {
        FetchExchangeFromJson exchangeRateTask = new FetchExchangeFromJson();
        exchangeRateTask.execute();
    }

    private void manipulateStringData(String line) {

        if (allDataFromJson != null && line.split(":").length > 1) {
            String currencyName = StringManipulator.clearApostrophe(line.split(":")[0]);
            String cleanString = StringManipulator.removeLastComma(line.split(":")[1].trim());
            Double currencyExchangeRate = Double.parseDouble(cleanString);

            allDataFromJson.add(new Currency(baseCurrency, currencyExchangeRate, currencyName));
        }
        if (line.split(":")[0].trim().equals(JsonParserConfig.JSON_RATES_KEY.getRealName())) {
            allDataFromJson = new ArrayList<>();
        }

        if (line.split(":")[0].trim().equals(JsonParserConfig.JSON_BASE_CURRENCY.getRealName())) {
            baseCurrency = line.split(":")[1].trim();
        }
    }

    class FetchExchangeFromJson extends AsyncTask<String, Void, ArrayList<Currency>> {

        @Override
        protected ArrayList<Currency> doInBackground(String... params) {

            URL url;
            try {
                Uri jsonUri = Uri.parse(JsonParserConfig.JSON_URL.getRealName());
                url = new URL(jsonUri.toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setReadTimeout(15 * 1000);
                connection.connect();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line = "";
                while ((line = reader.readLine()) != null) {
                    manipulateStringData(line);
                }
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }

            return allDataFromJson;
        }

        @Override
        protected void onPostExecute(ArrayList<Currency> currencyList) {
            if (reader != null) {
                dbManagerListener.passDataToDb(currencyList);
            } else {
                dbManagerListener.readDataFromDb();
            }
        }
    }

}
