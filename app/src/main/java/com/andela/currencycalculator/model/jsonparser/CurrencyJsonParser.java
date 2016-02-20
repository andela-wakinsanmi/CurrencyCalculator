package com.andela.currencycalculator.model.jsonparser;


import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Spykins on 18/02/2016.
 */
public class CurrencyJsonParser {
    HashMap<String, Double> allDataFromJson;
    public static String BASE_CURRENCY;

    private String jsonUrl =  "https://openexchangerates.org/api/latest.json?app_id=125d7e1c1f664d0488a4262f599038ae";

    public CurrencyJsonParser() {


    }

    public void loadRate()   {
        FetchExchangeFromJson exchangeRateTask = new FetchExchangeFromJson();
        exchangeRateTask.execute();
    }

    public class FetchExchangeFromJson extends AsyncTask<String, Void, String[]>{


        @Override
        protected String[] doInBackground(String... params) {

            URL url;
            BufferedReader reader = null;
            //StringBuilder stringBuilder;

            try
            {
                // create the HttpURLConnection
                Uri jsonUri = Uri.parse(jsonUrl);
                url = new URL(jsonUri.toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // just want to do an HTTP GET here
                connection.setRequestMethod("GET");

                // uncomment this if you want to write output to this url
                //connection.setDoOutput(true);

                // give it 15 seconds to respond
                //connection.setReadTimeout(15*1000);
                connection.connect();

                // read the output from the server
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                //stringBuilder = new StringBuilder();
                String jsonFile = "";

                String line = "";
                while ((line = reader.readLine()) != null)
                {
                    manipulateStringData(line);
                }
                //return stringBuilder.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                //throw e;
            }
            finally
            {
                // close the reader; this can throw an exception too, so
                // wrap it in another try/catch block.
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

    private void manipulateStringData(String line){

        if(allDataFromJson != null && line.split(":")[0].trim().length() > 1){
            String currencyName = line.split(":")[0].trim().replace("\"", "");
            Double currencyExchangeRate = Double.parseDouble(line.split(":")[1].trim().replace(",",""));
            allDataFromJson.put(currencyName,currencyExchangeRate);

        }
        if(line.split(":")[0].trim().equals("\"rates\"")){
            allDataFromJson = new HashMap<>();
        }

        if(line.split(":")[0].trim().equals("\"base\"")){
            CurrencyJsonParser.BASE_CURRENCY = line.split(":")[1].trim();
        }

    }

    public HashMap<String, Double> getAllDataFromJson(){
        return allDataFromJson;
    }



}


