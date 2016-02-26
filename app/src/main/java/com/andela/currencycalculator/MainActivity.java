package com.andela.currencycalculator;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.currencycalculator.adapter.SpinnerAdapter;
import com.andela.currencycalculator.manager.ParserDbManager;
import com.andela.currencycalculator.model.helper.SpinnerCurrency;
import com.andela.currencycalculator.model.helper.StringManipulator;
import com.andela.currencycalculator.model.jsonparser.JsonParserInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements JsonParserInterface,
        AdapterView.OnItemSelectedListener {

    ParserDbManager parserDbManager;
    Spinner spinnerFrom, spinnerTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        parserDbManager = ParserDbManager.getInstance(this);
        spinnerTo = (Spinner) findViewById(R.id.spinner_To);
        spinnerFrom = (Spinner) findViewById(R.id.spinner_from);

        if(isConnectionAvailable()){
            parserDbManager.getDataFromJson();
        } else if(parserDbManager.hasDatabase()){
            //load database data
            notifyActivity();
        } else {
            Toast.makeText(this,"You need to be connected to internet", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected void onStart() {
        //notifyActivity();
        super.onStart();
    }

    public void keyPressed(View view) {
        //show in textview...
        TextView inputTextView = (TextView) findViewById(R.id.inputTextView);
        String oldValueOnView = inputTextView.getText().toString();
        if (oldValueOnView.equals("0") || oldValueOnView.equals("")) {
            String buttonPressed = view.getTag().toString();
            inputTextView.setText(buttonPressed);
        } else {
            String buttonPressed = view.getTag().toString();
            inputTextView.setText(oldValueOnView + buttonPressed);

        }

    }

    public void functionKeyPressed(View view) {
        //send to the calculator brain to calculate
        TextView inputTextView = (TextView) findViewById(R.id.inputTextView);
        String oldValueOnView = inputTextView.getText().toString();
        //send value to calculator brain
        inputTextView.setText("");


    }

    public void answerKeyPressed(View view) {
        spinnerTo.performClick();

    }

    public void dotKeyPressed(View view) {
    }

    public void currencyFrom(View view) {
        spinnerFrom.performClick();
        //check what is selected
        //pass it to the model and do computation

    }

    public void currencyTo(View view) {
        spinnerTo.performClick();
        //check what is selected
        //pass it to the model and do computation
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        SpinnerCurrency valueSelected = (SpinnerCurrency) parent.getItemAtPosition(pos);
        if (parent.getId() == R.id.spinner_from) {
            Button buttonFrom = (Button) findViewById(R.id.currency_converting_From);
            buttonFrom.setText(valueSelected.getSpinnerCodeText());
            TextView currencyTextInput = (TextView) findViewById(R.id.currencyTextInput);
            currencyTextInput.setText(parserDbManager.getCurrencyIcon(valueSelected.getSpinnerCodeText()));

            //Do calculation based on what is selected

        } else {
            Button buttonFrom = (Button) findViewById(R.id.currency_converting_To);
            buttonFrom.setText(valueSelected.getSpinnerCodeText());
            TextView currencyTextAnswer = (TextView) findViewById(R.id.currencyTextAnswer);
            currencyTextAnswer.setText(parserDbManager.getCurrencyIcon(valueSelected.getSpinnerCodeText()));
            //Do calculation based on what is selected
        }

    }


    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void notifyActivity() {
        HashMap<String, ArrayList<String>> allCurrenciesInFile = parserDbManager.readAllCurrencyInFile();
        ArrayList<SpinnerCurrency> currencyKeys = new ArrayList<>();

        //Loop through Map and create SpinnerCurrencyOutOfit

        for(Map.Entry<String,ArrayList<String>> entry : allCurrenciesInFile.entrySet()){
            //the symbol code
            String code = entry.getKey();
            if(entry.getValue().size() > 0){
                String imageName = StringManipulator.replaceSpaceToLower(entry.getValue().get(0));
                int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());

                currencyKeys.add(new SpinnerCurrency(resID, code,imageName));
            }

        }
        spinnerFrom.setOnItemSelectedListener(this);
        spinnerTo.setOnItemSelectedListener(this);

        SpinnerAdapter adapter = new SpinnerAdapter(this,
                R.layout.spinner_layout,R.id.spinnerCodeView, currencyKeys);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

    }

    private boolean isConnectionAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
