package com.andela.currencycalculator;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.currencycalculator.adapter.SpinnerAdapter;
import com.andela.currencycalculator.calculatorbrain.CalculatorManager;
import com.andela.currencycalculator.calculatorbrain.CurrencyConstant;
import com.andela.currencycalculator.manager.ParserDbManager;
import com.andela.currencycalculator.model.currency.Currency;
import com.andela.currencycalculator.model.helper.SpinnerCurrency;
import com.andela.currencycalculator.model.helper.StringManipulator;
import com.andela.currencycalculator.model.jsonparser.JsonParserListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements JsonParserListener,
        AdapterView.OnItemSelectedListener {

    private ParserDbManager parserDbManager;
    private Spinner spinnerFrom, spinnerTo;
    private String spinnerFromSelectedText;
    private String spinnerToSelectedText;
    private CalculatorManager calculatorManager;
    private String inputNumber = "";
    private boolean currencyMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parserDbManager = ParserDbManager.getInstance(this);
        parserDbManager.setJsonParserListener(this);
        spinnerTo = (Spinner) findViewById(R.id.spinner_To);
        spinnerFrom = (Spinner) findViewById(R.id.spinner_from);

        if (isConnectionAvailable()) {
            parserDbManager.getDataFromJson();
        } else if (parserDbManager.hasDatabase()) {
            notifyActivity(parserDbManager.readAllCurrencyDataFromDb());
        } else {
            Toast.makeText(this, "You need to be connected to internet", Toast.LENGTH_LONG).show();
        }
        setSpinnerDisplay();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /*
        KeyPressed
        //show in textview...

     */
    public void keyPressed(View view) {
        if (inputNumber != null && inputNumber.length() < 10) {
            if (StringManipulator.isOperator(inputNumber)) {
                //calculatorManager.addOperator(view.getTag().toString());
                TextView inputTextView = (TextView) findViewById(R.id.inputTextView);
                inputTextView.setText("");
                inputNumber = "";
            }
            if (inputNumber != null && inputNumber.contains("ans" + CurrencyConstant.CURRENCY_DELIMETER)) {
                inputNumber = "";
            }

            TextView inputTextView = (TextView) findViewById(R.id.inputTextView);
            inputNumber = inputTextView.getText().toString();

            if (inputNumber.equals("0") || inputNumber.equals("")) {
                String buttonPressed = view.getTag().toString();
                inputTextView.setText(buttonPressed);
                inputNumber = buttonPressed;
                updateOutputView();

            } else {
                String buttonPressed = view.getTag().toString();
                inputNumber = inputNumber + buttonPressed;
                inputTextView.setText(inputNumber);
                updateOutputView();

            }

        } else {
            Toast.makeText(this, "maximum amount reached", Toast.LENGTH_LONG);
        }

    }

    private void updateOutputView() {
        TextView outputView = (TextView) findViewById(R.id.outputText);
        if (currencyMode && calculatorManager != null && !StringManipulator.isOperator(inputNumber)
                && inputNumber != null && !inputNumber.equals("") && !inputNumber.contains("ans")) {
            Log.d("waleola", "check for this... gets called");
            String result = calculatorManager.exchangeFromCurrencyTo(spinnerFromSelectedText,
                    spinnerToSelectedText, inputNumber);
            outputView.setText(result);
        } else {
            outputView.setText("");
        }

    }

    /*
        //calculatorManager.addValueToCalculate(spinnerFromSelectedText + ":" + previousAnswer);
        //send to the calculator brain to calculate
         //String oldValueOnView = inputTextView.getText().toString();

        //send value to calculator brain
        //Log.d("waleola",spinnerFromSelectedText + ":" + inputNumber);
        //calculatorManager.addValueToCalculate(spinnerFromSelectedText + ":" + inputNumber);



     */
    public void functionKeyPressed(View view) {
        if (inputNumber.contains("ans")) {
            inputNumber = inputNumber.split(
                    CurrencyConstant.CURRENCY_DELIMETER)[1].trim();
        }
        //TextView inputTextView = (TextView) findViewById(R.id.inputTextView);
        //TextView outputView = (TextView) findViewById(R.id.outputText);
        //checking that i am sending the most recent operator
        if (!inputNumber.equals("") && inputNumber != null && calculatorManager != null) {
            if (currencyMode) {
                String sendingInto = spinnerFromSelectedText +
                        CurrencyConstant.CURRENCY_DELIMETER + inputNumber;
                calculatorManager.addInputIntoArray(sendingInto);

            } else {
                calculatorManager.addInputIntoArray(inputNumber);
            }
            inputNumber = view.getTag().toString();
            calculatorManager.onPressedOfFunctionKey(inputNumber);

        }

    }
    /*
        //calculatorManager.addValueToCalculate(spinnerFromSelectedText + ":" + inputNumber);
        //get solution from the arraylist in CalculatorManager
        //outputView.setText(calculatorManager.performCalculation(spinnerToSelectedText));
        //inputNumber= "ans: " + calculatorManager.performCalculation(spinnerToSelectedText);
        //clear the arrayList and add the new value..... if operator key is selected afterwards..
     */

    public void answerKeyPressed(View view) {
        TextView outputView = (TextView) findViewById(R.id.outputText);
        TextView inputView = (TextView) findViewById(R.id.inputTextView);

        if (!inputNumber.contains("ans")) {
            if (currencyMode) {
                calculatorManager.addInputIntoArray(spinnerFromSelectedText +
                        CurrencyConstant.CURRENCY_DELIMETER + inputNumber);
                inputNumber = calculatorManager.performOperation(spinnerToSelectedText);
                updateOutputView();
            } else {
                calculatorManager.addInputIntoArray(inputNumber);
                inputNumber = calculatorManager.performOperation("");
            }
            outputView.setText(inputNumber);
            inputNumber = "ans " + CurrencyConstant.CURRENCY_DELIMETER + inputNumber;
            calculatorManager.reInitializeArray();
            inputView.setText("");
        }

    }

    public void dotKeyPressed(View view) {

        TextView inputTextView = (TextView) findViewById(R.id.inputTextView);
        if (inputNumber != null && !inputNumber.contains(".") && !inputNumber.equals("")) {
            inputNumber = inputNumber + ".";
        }
        if (inputNumber != null && inputNumber.contains("ans")) {
            inputNumber = "";
        }
        inputTextView.setText(inputNumber);
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
            updateSpinnerButtonFrom(valueSelected);
        } else {
            updateSpinnerButtonTo(valueSelected);
        }

    }

    private void updateSpinnerButtonFrom(SpinnerCurrency valueSelected) {
        Button buttonFrom = (Button) findViewById(R.id.currency_converting_From);
        spinnerFromSelectedText = valueSelected.getSpinnerCodeText();
        buttonFrom.setText(spinnerFromSelectedText);
        TextView currencyTextInput = (TextView) findViewById(R.id.currencyTextInput);
        currencyTextInput.setText(parserDbManager.getCurrencyIcon(spinnerFromSelectedText));
        //Do calculation based on what is selected
        if (inputNumber != null && !inputNumber.equals("")) {
            updateOutputView();
        }
    }

    private void updateSpinnerButtonTo(SpinnerCurrency valueSelected) {
        Button buttonTo = (Button) findViewById(R.id.currency_converting_To);
        spinnerToSelectedText = valueSelected.getSpinnerCodeText();
        buttonTo.setText(spinnerToSelectedText);
        TextView currencyTextAnswer = (TextView) findViewById(R.id.currencyTextAnswer);
        currencyTextAnswer.setText(parserDbManager.getCurrencyIcon(spinnerToSelectedText));
        if (inputNumber != null && !inputNumber.equals("")) {
            updateOutputView();
        }
        //Do calculation based on what is selected
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void notifyActivity(ArrayList<Currency> currencies) {
        //Parsing value from the database/parser into the currency converter....
        calculatorManager = new CalculatorManager(currencies, currencyMode);
        calculatorManager.switchMode(currencyMode);
        for (Currency currency : currencies) {
            Log.d("waleola", " " + currency.toString());
        }
    }

    private void setSpinnerDisplay() {
        HashMap<String, ArrayList<String>> allCurrenciesInFile = parserDbManager.readAllCurrencyInFile();
        ArrayList<SpinnerCurrency> currencyKeys = new ArrayList<>();

        //Loop through Map and create SpinnerCurrencyOutOfit

        for (Map.Entry<String, ArrayList<String>> entry : allCurrenciesInFile.entrySet()) {
            //the symbol code
            String code = entry.getKey();
            if (entry.getValue().size() > 0) {
                String imageName = StringManipulator.replaceSpaceToLower(entry.getValue().get(0));
                int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());

                currencyKeys.add(new SpinnerCurrency(resID, code, imageName));
            }

        }
        spinnerFrom.setOnItemSelectedListener(this);
        spinnerTo.setOnItemSelectedListener(this);

        SpinnerAdapter adapter = new SpinnerAdapter(this,
                R.layout.spinner_layout, R.id.spinnerCodeView, currencyKeys);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

    }

    private boolean isConnectionAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /*
        ..... set the Mode in Model.... and erase the screen and input text
              In the model, reset the ArrayList
              Switch the mode...

     */
    public void switchMode(View view) {
        currencyMode = !currencyMode;
        //reset screen and input....
        clearScreen();
        Button fromButton = (Button) findViewById(R.id.currency_converting_From);
        Button toButton = (Button) findViewById(R.id.currency_converting_To);
        Button modeButton = (Button) findViewById(R.id.currency_switch);

        if (!currencyMode) {
            toButton.setEnabled(false);
            fromButton.setEnabled(false);
            modeButton.setText("CALC");
            TextView currencyTextInput = (TextView) findViewById(R.id.currencyTextInput);
            TextView currencyTextOutput = (TextView) findViewById(R.id.currencyTextAnswer);
            currencyTextInput.setText("");
            currencyTextOutput.setText("");

            calculatorManager.switchMode(currencyMode);
            clearScreen();
            //in calculator mode
        } else {
            fromButton.setEnabled(true);
            toButton.setEnabled(true);
            modeButton.setText("CURR");
            TextView outputView = (TextView) findViewById(R.id.currencyTextAnswer);
            TextView inputView = (TextView) findViewById(R.id.currencyTextInput);
            outputView.setText(parserDbManager.getCurrencyIcon(spinnerToSelectedText));
            inputView.setText(parserDbManager.getCurrencyIcon(spinnerFromSelectedText));

            calculatorManager.switchMode(currencyMode);
            clearScreen();
            //in Currency mode
        }
    }

    private void clearScreen() {
        TextView inputTextView = (TextView) findViewById(R.id.inputTextView);
        TextView outputTextView = (TextView) findViewById(R.id.outputText);
        inputTextView.setText("");
        outputTextView.setText("");

        inputNumber = "";
    }

    public void deleteButtonClicked(View view) {
        TextView inputTextView = (TextView) findViewById(R.id.inputTextView);
        TextView outPutTextView = (TextView) findViewById(R.id.outputText);

        if (!inputNumber.contains("ans") && (inputNumber != null && !inputNumber.equals("")) && inputNumber.length() != 0) {
            inputNumber = inputNumber.substring(0, inputNumber.length() - 1);
            inputTextView.setText(inputNumber);
        } else if (inputNumber.contains("ans")) {
            outPutTextView.setText("");
        }
    }
}
