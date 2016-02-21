package com.andela.currencycalculator.model.jsonparser;


import android.net.Uri;
import android.os.AsyncTask;

import com.andela.currencycalculator.model.manager.CurrencyModel;
import com.andela.currencycalculator.model.manager.ParserDbManager;

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
    private ArrayList<CurrencyModel> allDataFromJson;
    private  String baseCurrency;
    private ParserDbManager parserDbManager;

    public CurrencyJsonParser(ParserDbManager parserDbManager) {
        this.parserDbManager = parserDbManager;
    }

    public void loadRate()   {
        FetchExchangeFromJson exchangeRateTask = new FetchExchangeFromJson();
        exchangeRateTask.execute();
    }

    private void manipulateStringData(String line){

        if(allDataFromJson != null && line.split(":")[0].trim().length() > 1){
            String currencyName = line.split(":")[0].trim().replace("\"", "");
            Double currencyExchangeRate = Double.parseDouble(line.split(":")[1].trim().replace(",",""));
            allDataFromJson.add(new CurrencyModel(baseCurrency, currencyExchangeRate, currencyName));
        }
        if(line.split(":")[0].trim().equals(JsonParserConfig.JSON_RATES_KEY.getRealName())){
            allDataFromJson = new ArrayList<>();
        }

        if(line.split(":")[0].trim().equals(JsonParserConfig.JSON_BASE_CURRENCY.getRealName())){
            baseCurrency = line.split(":")[1].trim();
        }

    }

    public class FetchExchangeFromJson extends AsyncTask<String, Void, String[]>{


        @Override
        protected String[] doInBackground(String... params) {

            URL url;
            BufferedReader reader = null;
            try
            {
                // create the HttpURLConnection
                Uri jsonUri = Uri.parse(JsonParserConfig.JSON_URL.getRealName());
                url = new URL(jsonUri.toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // just want to do an HTTP GET here
                connection.setRequestMethod("GET");

                // uncomment this if you want to write output to this url
                //connection.setDoOutput(true);

                // give it 15 seconds to respond
                connection.setReadTimeout(15*1000);
                connection.connect();

                // read the output from the server
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line = "";
                while ((line = reader.readLine()) != null)
                {
                    manipulateStringData(line);
                }
                parserDbManager.parseDataFromJson(allDataFromJson);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (reader != null)
                {
                    try
                    {
                        reader.close();
                    }
                    catch (IOException ioe)
                    {
                        ioe.printStackTrace();
                    }
                }
            }

            return null;
        }
    }

}


