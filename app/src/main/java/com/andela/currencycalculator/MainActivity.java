package com.andela.currencycalculator;

import android.app.ProgressDialog;
import android.content.Context;

import android.graphics.Color;
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
import com.andela.currencycalculator.manager.CalculatorManager;
import com.andela.currencycalculator.calculatorbrain.CurrencyConstant;
import com.andela.currencycalculator.manager.ParserDbManager;
import com.andela.currencycalculator.model.currency.Currency;
import com.andela.currencycalculator.model.currency.TopTen;
import com.andela.currencycalculator.model.helper.SpinnerCurrency;
import com.andela.currencycalculator.model.helper.StringManipulator;
import com.andela.currencycalculator.model.jsonparser.JsonParserListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Akinsanmi Waleola
 * @since 2/02/2016.
 * @version 1
 */

public class MainActivity extends AppCompatActivity implements JsonParserListener,
        AdapterView.OnItemSelectedListener {

    private ParserDbManager parserDbManager;
    private Spinner spinnerFrom, spinnerTo;
    private String spinnerFromSelectedText;
    private String spinnerToSelectedText;
    private CalculatorManager calculatorManager;
    /**
     * inputNumber used to save keypressed by user and answer
     * <p>
     *     inputNumber used in :
     *     @{@link MainActivity#keyPressed(View)}

     * </p>
     *

     */
    private String inputNumber = "";
    private boolean currencyMode = true;
    private ArrayList<String> allCurrencyInput;
    private boolean sameCurrencySelected = false;
    private ProgressDialog progress;
    private Button functionButtonPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parserDbManager = ParserDbManager.getInstance(this);
        parserDbManager.setJsonParserListener(this);
        spinnerTo = (Spinner) findViewById(R.id.spinner_To);
        spinnerFrom = (Spinner) findViewById(R.id.spinner_from);
        allCurrencyInput = new ArrayList<>();
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setIndeterminate(false);
        functionButtonPressed = new Button(this);

        initialSetUp();
        setListenerOnButton();
    }

    private void initialSetUp() {
        if (isConnectionAvailable()) {
            progress.setMessage("Fetching exchange rate");
            progress.show();
            parserDbManager.getDataFromJson();

        } else if (parserDbManager.hasDatabase()) {
            progress.setMessage("reading from database");
            progress.show();
            notifyActivity(parserDbManager.readAllCurrencyDataFromDb());
        } else {
            Toast.makeText(this, "You need to be connected to internet", Toast.LENGTH_LONG).show();
            progress.setMessage("You need to be connected to internet");
            progress.show();
        }
    }

    private void setListenerOnButton() {
        Button clearButton = (Button) findViewById(R.id.buttonCancel);
        clearButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TextView inputTextView = (TextView) findViewById(R.id.inputTextView);
                inputTextView.setText("");
                inputNumber = "";
                updateOutputView();
                return true;
            }
        });

    }

    /**
     * KeyPressed is called when a user presses a keypad (0-9) in the view
     * <p>
     *     When the button is pressed
     *     Check if inputNumber doesn't contain ans and it's not null
     *     save the value in the variable {@link MainActivity#inputNumber}.
     *     This method has helper methods
     *     {@link MainActivity#checkIfInputIsOperator()}
     *     {@link MainActivity#updateOutputView()}
     *     {@link MainActivity#checkIfInputIsZero(TextView, View)}
     * </p>
     * @param view which is a button

     */

    public void keyPressed(View view) {
        clearRedButton(view);
        if ((inputNumber != null) &&
                (inputNumber.length() < 10 || inputNumber.contains("ans"))) {

            checkIfInputIsOperator();
            TextView inputTextView = (TextView) findViewById(R.id.inputTextView);
            inputNumber = inputTextView.getText().toString();
            checkIfInputIsZero(inputTextView, view);

        } else {
            Toast.makeText(this, "maximum amount reached", Toast.LENGTH_SHORT).show();
        }
        updateOutputView();

    }

    /**
     * helper method for {@link MainActivity#keyPressed(View)}
     */

    private void checkIfInputIsOperator() {
        if (StringManipulator.isOperator(inputNumber)) {
            TextView inputTextView = (TextView) findViewById(R.id.inputTextView);
            inputTextView.setText("");
            inputNumber = "";
        } else if (inputNumber != null && inputNumber.contains("ans")) {
            inputNumber = "";
        }
    }

    /**
     * helper method for {@link MainActivity#keyPressed(View)}
     */

    private void checkIfInputIsZero(TextView inputTextView, View view) {
        String buttonPressed = view.getTag().toString();

        if (inputNumber.equals("0") || inputNumber.equals("")) {
            inputTextView.setText(buttonPressed);
            inputNumber = buttonPressed;
        } else {
            inputNumber = inputNumber + buttonPressed;
            inputTextView.setText(inputNumber);
        }
        updateOutputView();
    }

    /**
     * Updates the outputview based on the inputText
     * helper method for {@link MainActivity#keyPressed(View)}
     */
    private void updateOutputView() {
        TextView outputView = (TextView) findViewById(R.id.outputText);
        boolean flag = currencyMode && (!StringManipulator.isOperator(inputNumber)) &&
                (inputNumber != null && !inputNumber.equals("")) && !inputNumber.contains("ans");

        if (flag) {
            String result = calculatorManager.exchangeFromCurrencyTo(spinnerFromSelectedText,
                    spinnerToSelectedText, inputNumber);

            outputView.setText(result);
        } else {
            outputView.setText("");
        }

    }

    /**
     * Method call when operation button is pressed in view
     * <p>
     * Function key pressed takes the inputNumber,
     * Adds it into the Brain ArrayList
     * It also calls the onPressedFunctionKey to register the function in the ArrayList
     * <b> Helper Menthods </b>
     * <ul>
     *     <li> {@link MainActivity#switchFunctionKeyColor(Button)}</li>
     *     <li>{@link CalculatorManager#addValueToArray(String)}</li>
     *     <li>{@link CalculatorManager#onPressedOfFunctionKey(String)}</li>
     * </ul>
     *
     * The operator button is passed into {@link MainActivity#inputNumber}
     *  </p>
     * @param view is the button pressed in the view
     */
    public void functionKeyPressed(View view) {

        switchFunctionKeyColor((Button)view);
        if (inputNumber.contains("ans")) {
            inputNumber = inputNumber.split(CurrencyConstant.CURR_DELIMITER)[1].trim();
        }

        if (inputNumber != null && !inputNumber.equals("")) {
            if (currencyMode) {
                String currCodeAndValue = spinnerFromSelectedText + CurrencyConstant.CURR_DELIMITER
                        + inputNumber;

                updateFunctionKey(view,currCodeAndValue);
                calculatorManager.addValueToArray(currCodeAndValue);
            } else {
                updateFunctionKey(view,inputNumber);
                calculatorManager.addValueToArray(inputNumber);
            }
            inputNumber = view.getTag().toString();
            calculatorManager.onPressedOfFunctionKey(inputNumber);
        }
        updateHistoryTextView();
    }

    /**
     * helper method for {@link MainActivity#functionKeyPressed(View)}
     */

    private void switchFunctionKeyColor(Button button) {
        if (button != functionButtonPressed){
            functionButtonPressed.setTextColor(Color.parseColor("#FFFFFF"));
            button.setTextColor(Color.parseColor("#FF0000"));
            functionButtonPressed = button;
        }
    }

    private void updateHistoryTextView() {
        TextView historyTextView  = (TextView) findViewById(R.id.historyTextView);
        String valuesInArray = allCurrencyInput.toString();
        valuesInArray = valuesInArray.replace("[","");
        valuesInArray = valuesInArray.replace("]","");
        valuesInArray = valuesInArray.replace(",","");
        historyTextView.setText(valuesInArray);
    }


    private void updateFunctionKey(View view, String currCodeAndValue){
        if(!StringManipulator.isOperator(inputNumber)) {
            allCurrencyInput.add(currCodeAndValue);
            allCurrencyInput.add(view.getTag().toString());
        } else {
            allCurrencyInput.remove(allCurrencyInput.size()-1);
            allCurrencyInput.add(view.getTag().toString());
        }
    }

    /**
     *
     * <p>
     * adds the last input value {@link MainActivity#inputNumber} into the calculator Brain
     * calculatorManager.addValueToCalculate(spinnerFromSelectedText + ":" + inputNumber);
     * It calls the performCalculation {@link CalculatorManager#performOperation(String)}
     * which returns the solution(String) from the arraylist in CalculatorManager
     * Saves the answer in inputText {@link MainActivity#inputNumber}  as "ans: " + the answer returned
     * clear the brain arrayList.
     * It adds the new answer, if operator key is selected afterwards..
     * </p>
     * @param view is a Button
     */
    public void answerKeyPressed(View view) {
        TextView outputView = (TextView) findViewById(R.id.outputText);
        TextView inputView = (TextView) findViewById(R.id.inputTextView);

        if (inputNumber != null && !inputNumber.contains("ans")) {
            performNewCalculation(inputView, outputView);
        } else {
            calculatorManager.reInitializeArray();
        }

        updateHistoryTextView();
        sameCurrencySelected = false;
        allCurrencyInput.clear();
        clearRedButton(view);
    }

    private void performNewCalculation(TextView inputView, TextView outputView) {
        if (currencyMode) {
            performCurrencyModeOperation();
            performOperationBasedOnMode();
        } else {
            performCalculatorModeOperation();
        }
        outputView.setText(inputNumber);
        inputNumber = "ans " + CurrencyConstant.CURR_DELIMITER + inputNumber;
        calculatorManager.reInitializeArray();
        inputView.setText("");
    }

    /**
     * This runs through the arrayList {@link MainActivity#allCurrencyInput }
     * Checks if the currency value in the arrayList is same thing
     * Then the program switch to calculator mode
     */

    private void performCurrencyModeOperation() {
        if(!inputNumber.equals("")) {
            String newValue = spinnerFromSelectedText + CurrencyConstant.CURR_DELIMITER + inputNumber;
            calculatorManager.addValueToArray(newValue);
            allCurrencyInput.add(newValue);
        }

        for (String currencyValue : allCurrencyInput) {
            if (currencyValue.contains(spinnerToSelectedText) ||
                    StringManipulator.isOperator(currencyValue)) {

                sameCurrencySelected = true;
            } else {
                sameCurrencySelected = false;
                break;
            }
        }
    }

    private void performOperationBasedOnMode() {
        if (sameCurrencySelected) {
            switchCurrencyModeToCalculatorMode();
        } else {
            convertAllCurrenciesToBaseAndCalculate();
            updateOutputView();
        }
    }

    private void switchCurrencyModeToCalculatorMode() {
        calculatorManager.reInitializeArray();
        calculatorManager.switchMode(false);
        String value;
        for (String currencyValue : allCurrencyInput) {
            if(currencyValue.contains(CurrencyConstant.CURR_DELIMITER)) {
                value = currencyValue.split(CurrencyConstant.CURR_DELIMITER)[1];
                performOperationOn(value, currencyValue);
            } else if (StringManipulator.isOperator(currencyValue)) {
                calculatorManager.onPressedOfFunctionKey(currencyValue);
            }
        }
        sameCurrencySelected = false;
        inputNumber = calculatorManager.performOperation("");
        calculatorManager.reInitializeArray();
        calculatorManager.switchMode(true);
    }

    private void performOperationOn(String value, String currencyValue) {

        if (currencyValue.contains(spinnerToSelectedText)) {
            calculatorManager.addValueToArray(value);
        } else if (StringManipulator.isOperator(value)) {
            calculatorManager.onPressedOfFunctionKey(value);
        }
    }

    private void convertAllCurrenciesToBaseAndCalculate() {
        calculatorManager.switchMode(false);
        for (String currencyValue : allCurrencyInput) {

            if(!StringManipulator.isOperator(currencyValue) && !currencyValue.equals("")){
                String currFrom = currencyValue.split(CurrencyConstant.CURR_DELIMITER)[0];
                String value = currencyValue.split(CurrencyConstant.CURR_DELIMITER)[1];
                if(!StringManipulator.isOperator(value)) {
                    calculatorManager.addValueToArray(calculatorManager.exchangeFromCurrencyTo(
                            currFrom, spinnerToSelectedText, value));
                }

            } else{
                calculatorManager.onPressedOfFunctionKey(currencyValue);
            }

        }
        inputNumber = calculatorManager.performOperation("");
        calculatorManager.reInitializeArray();
        calculatorManager.switchMode(true);

    }

    private void performCalculatorModeOperation() {
        if(!inputNumber.equals("")) {
            allCurrencyInput.add(inputNumber);
        }
        calculatorManager.reInitializeArray();
        for(String values : allCurrencyInput) {
            if(!StringManipulator.isOperator(values) && !values.equals("")) {
                calculatorManager.addValueToArray(values);
            } else {
                calculatorManager.onPressedOfFunctionKey(values);
            }
        }
        inputNumber = calculatorManager.performOperation("");
        currencyMode = false;
    }

    public void dotKeyPressed(View view) {

        TextView inputTextView = (TextView) findViewById(R.id.inputTextView);
        if(inputNumber.contains("ans")) {
            inputNumber = "0.";
        } else if (inputNumber != null && !inputNumber.contains(".") &&
                !StringManipulator.isOperator(inputNumber) && !inputNumber.equals("")) {
            inputNumber = inputNumber + ".";
        } else if (StringManipulator.isOperator(inputNumber) || inputNumber.equals("")) {
            inputNumber = "0.";

        }
        inputTextView.setText(inputNumber);
    }

    /**
        currencyFrom and CurrencyTo is onclick method on the Button
        that represents the Spinner in the UI
        but when the button is clicked. It calls the actual spinner object
        to wake up..
     */
    public void currencyFrom(View view) {
        spinnerFrom.performClick();
    }

    public void currencyTo(View view) {
        spinnerTo.performClick();
    }

    /**
     * AdapterView.OnItemSelectedListener call back method
     * <p>
     *     When an item on the Spinner is selected
     *     The value is updated by calling the
     *     {@link MainActivity#updateSpinnerButtonTo(SpinnerCurrency)}
     * </p>
     * @param parent
     * @param view
     * @param pos
     * @param id
     */
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        SpinnerCurrency valueSelected = (SpinnerCurrency) parent.getItemAtPosition(pos);
        if (parent.getId() == R.id.spinner_from) {
            updateSpinnerButtonFrom(valueSelected);
        } else {
            updateSpinnerButtonTo(valueSelected);
        }

    }

    /**
     * The method assigns the code selected from the Spinner
     * and assigns it to the variable {@link MainActivity#spinnerFromSelectedText}
     * or {@link MainActivity#spinnerToSelectedText} based the spinner clicked
     *
     * This update the outputview based on the selected value
     * in the spinner
     * @param valueSelected
     */

    private void updateSpinnerButtonFrom(SpinnerCurrency valueSelected) {
        Button buttonFrom = (Button) findViewById(R.id.currency_converting_From);
        spinnerFromSelectedText = valueSelected.getSpinnerCodeText();
        buttonFrom.setText(spinnerFromSelectedText);
        TextView currencyTextInput = (TextView) findViewById(R.id.currencyTextInput);
        currencyTextInput.setText(parserDbManager.getCurrencyIcon(spinnerFromSelectedText));
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
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     * This is a call back method
     * This method is called from the ParserDbManager to notify the activity
     * when the ParserDbManager gets the Json file fro CurrencyJsonParser
     * @param currencies
     */

    @Override
    public void notifyActivity(ArrayList<Currency> currencies) {
        calculatorManager = new CalculatorManager(currencies, currencyMode);
        calculatorManager.switchMode(currencyMode);
        setSpinnerDisplay();
        progress.dismiss();

    }
    /**
     * This method sets the Spinner Display
     * <p>
     *      Method Loops through allCurrenciesInFile, Sort the Map in Alphabetical order
     *      Adds, the Top ten Currencies and populate the ArrayList<SpinnerCurrency>
     *
     *      The ArrayList currencyKeys is of type {@link SpinnerCurrency}
     *      The arrayList (currencyKeys) will be parsed in the SpinnerAdapter
     *      {@link SpinnerAdapter}
     *      The ArrayList allCurrenciesInFile maps String(currencyCode) to
     *      value(ArrayList of string => index[0] country and index[1] is the symbol)
     *
     *      This method calls {@link MainActivity#addItemInSpinnerArrayList(TreeMap,
     *      ArrayList, ArrayList)}
     *      which runs through the currencies from file and populates the currencyKeys
     *      arrayList.
     *
     *
     * </p>
     */
    private void setSpinnerDisplay() {
        HashMap<String, ArrayList<String>> allCurrenciesInFile = parserDbManager.readAllCurrencyInFile();

        ArrayList<SpinnerCurrency> currencyKeys = new ArrayList<>();
        TreeMap<String, ArrayList<String>> sortedCurrencies = new TreeMap<>(allCurrenciesInFile);

        TopTen topTen = new TopTen();
        ArrayList<String> allTopTen = topTen.getTopTen();

        addItemInSpinnerArrayList(sortedCurrencies,currencyKeys,allTopTen);

        spinnerFrom.setOnItemSelectedListener(this);
        spinnerTo.setOnItemSelectedListener(this);

        SpinnerAdapter adapter = new SpinnerAdapter(this,
                R.layout.spinner_layout, R.id.spinnerCodeView, currencyKeys);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

    }

    private void addItemInSpinnerArrayList(TreeMap<String, ArrayList<String>> sortedCurrencies,
                                           ArrayList<SpinnerCurrency> currencyKeys,
                                           ArrayList<String> allTopTen) {

        for (Map.Entry<String, ArrayList<String>> entry : sortedCurrencies.entrySet()) {
            //the symbol code
            String code = entry.getKey();
            if (entry.getValue().size() > 0) {
                String imageName = StringManipulator.replaceSpaceToLower(entry.getValue().get(0));
                int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());
                String countryName = parserDbManager.getCurrencyCountry(code);
                if (allTopTen.contains(code)) {
                    currencyKeys.add(0, new SpinnerCurrency(resID, code, countryName));
                } else {
                    currencyKeys.add(new SpinnerCurrency(resID, code, countryName));
                }
            }

        }
    }

    private boolean isConnectionAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     *set the Mode in Model.... and erase the screen and input text
     * In the model, reset the ArrayList
     * Switch the mode...
     */
    public void switchMode(View view) {
        currencyMode = !currencyMode;
        //reset screen and input....
        clearScreen();
        Button fromButton = (Button) findViewById(R.id.currency_converting_From);
        Button toButton = (Button) findViewById(R.id.currency_converting_To);
        Button modeButton = (Button) findViewById(R.id.currency_switch);

        if (!currencyMode) {
            switchMode(toButton, fromButton, modeButton, false);
        } else {
            switchMode(toButton, fromButton, modeButton, true);
        }
        clearRedButton(view);
        allCurrencyInput.clear();
        updateHistoryTextView();
    }

    private void switchMode(Button toButton, Button fromButton, Button modeButton, boolean flag) {
        TextView outputView = (TextView) findViewById(R.id.currencyTextAnswer);
        TextView inputView = (TextView) findViewById(R.id.currencyTextInput);
        fromButton.setEnabled(flag);
        toButton.setEnabled(flag);

        if(flag) {
            modeButton.setText(R.string.CurrencyCode);
            outputView.setText(parserDbManager.getCurrencyIcon(spinnerToSelectedText));
            inputView.setText(parserDbManager.getCurrencyIcon(spinnerFromSelectedText));
            calculatorManager.switchMode(currencyMode);

        } else {
            modeButton.setText(R.string.CalculatorCode);
            inputView.setText("");
            outputView.setText("");
            calculatorManager.switchMode(currencyMode);
        }

        clearScreen();
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

        if(inputNumber.equals("") && allCurrencyInput.size() != 0) {
            manipulateArray();
        }
        updateHistoryTextView();
        clearRedButton(view);
        updateOutputView();
    }

    private void manipulateArray() {
        TextView inputText = (TextView) findViewById(R.id.inputTextView);
        String lastInput = allCurrencyInput.get(allCurrencyInput.size() - 1);
        allCurrencyInput.remove(allCurrencyInput.size()-1);
        if(currencyMode) {
            performCurrArrayManipulation(lastInput);
        } else {
            if(!StringManipulator.isOperator(lastInput)) {
                inputNumber = lastInput;
            }
        }
        inputText.setText(inputNumber);
        updateHistoryTextView();
        updateOutputView();
    }

    private void performCurrArrayManipulation(String lastInput) {
        if(lastInput.contains(CurrencyConstant.CURR_DELIMITER)) {
            String[] splitValue = lastInput.split(CurrencyConstant.CURR_DELIMITER);
            if(!StringManipulator.isOperator(splitValue[1])) {
                inputNumber = splitValue[1];
            }
        }
    }

    private void clearRedButton(View view) {
        functionButtonPressed.setTextColor(Color.parseColor("#FFFFFF"));
        functionButtonPressed = (Button) view;
    }
}
