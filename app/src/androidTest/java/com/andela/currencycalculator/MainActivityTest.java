package com.andela.currencycalculator;

import android.support.test.rule.ActivityTestRule;
import android.support.test.espresso.NoMatchingViewException;

import com.andela.currencycalculator.calculatorbrain.CalculatorConstant;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.Matchers.not;

/**
 * Created by Spykins on 04/03/2016.
 */

public class MainActivityTest {
    Random r = new Random();

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSwitchMode() throws Exception {

        onView(withText("CURR"))
                .check(matches(withText("CURR")));
        try {
            onView(withText("CALC"))
                    .check(matches(not(isDisplayed())));
        } catch (NoMatchingViewException nmve) {
            nmve.printStackTrace();
        }
        onView(withText("CURR"))
                .perform(click());
        try {
            onView(withText("CURR"))
                    .check(matches(not(isDisplayed())));
        } catch (NoMatchingViewException nmve) {
            nmve.printStackTrace();
        }
        onView(withText("CALC"))
                .check(matches(isDisplayed()));
        onView(withText("CALC"))
                .perform(click());
        onView(withText("CURR"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testKeyPressed() throws Exception {
        onView(withText("CURR")).check(matches(withText("CURR")))
                .perform(click());
        onView(withText("1")).check(matches(withText("1"))).perform(click());
        onView(withId(R.id.inputTextView)).check(matches(withText("1")));

        int j = 0;
        String output = "1";

        for(int i = 0; i<5; i++){
            j = r.nextInt(9);
            output = output + j;
            onView(withText((String.valueOf(j)))).check(matches(withText(String.valueOf(j)))).perform(click());
            onView(withId(R.id.inputTextView)).check(matches(withText(output)));
        }

    }

    @Test
    public void testFunctionKeyPressed() throws Exception {
        onView(withText("CURR")).check(matches(withText("CURR")))
                .perform(click());

        performFirstInput();

        onView(withText(CalculatorConstant.CALC_MULTIPLY.getRealName())).check(
                matches(withText(CalculatorConstant.CALC_MULTIPLY.getRealName()))).perform(click());

        String secondInput = performSecondInput();

        onView(withId(R.id.inputTextView)).check(matches(withText(secondInput)));

    }

    private String performFirstInput() {
        int j = 0;
        String output = "";

        //for(int i = 0; i<2; i++){
            j = r.nextInt(9);
            output = output + j;
            onView(withText("2")).check(matches(withText(String.valueOf("2")))).perform(click());
            //onView(withId(R.id.inputTextView)).check(matches(withText(output)));
        //}
        return "2";
    }

    private String performSecondInput(){
        int j2 = 0;
        String output2 = "";
         j2 = r.nextInt(9);
        output2 = output2 + j2;
        onView(withText((String.valueOf(5)))).check(matches(withText(String.valueOf(5)))).perform(click());

        return "5";
    }

    @Test
    public void testAnswerKeyPressed() throws Exception {

        onView(withText("CURR")).check(matches(withText("CURR")))
                .perform(click());

        String firstInput = performFirstInput();

        onView(withText(CalculatorConstant.CALC_MULTIPLY.getRealName())).check(
                matches(withText(CalculatorConstant.CALC_MULTIPLY.getRealName()))).perform(click());

        String secondInput = performSecondInput();

        String answer = String.valueOf(Integer.parseInt(firstInput) * Integer.parseInt(secondInput));

        onView(withText("=")).check(matches(withText("="))).perform(click());

        onView(withId(R.id.outputText)).check(matches(isDisplayed()));

    }

    @Test
    public void testDotKeyPressed() throws Exception {

        int j = 0;
        String output = "0.";

        for(int i = 0; i<2; i++){
            onView(withId(R.id.buttonDot)).perform(click());
            j = r.nextInt(9);
            output = output + j;
            onView(withText((String.valueOf(j)))).perform(click());
        }
        onView(withId(R.id.inputTextView)).check(matches(withText(output)));

    }

    @Test
    public void testCurrencyFrom() throws Exception {
        //onView(withId(R.id.spinner_from));
        //onData(allOf(instanceOf(SpinnerCurrency.class), is("NGN"))).perform(click());
        //onData(allOf(is(instanceOf(String.class)), is("Americano"))).perform(click());
        onView(withId(R.id.currency_converting_From)).perform(click());
        onView(withText("NGN")).perform(click());
        onView(withId(R.id.currency_converting_From)).check(matches(withText("NGN")));

        onView(withId(R.id.currency_converting_From)).perform(click());
        onView(withText("EUR")).perform(click());
        onView(withId(R.id.currency_converting_From)).check(matches(withText("EUR")));

        onView(withId(R.id.currency_converting_From)).perform(click());
        onView(withText("AFN")).perform(click());
        onView(withId(R.id.currency_converting_From)).check(matches(withText("AFN")));

        onView(withId(R.id.currency_converting_From)).perform(click());
        onView(withText("AZN")).perform(click());
        onView(withId(R.id.currency_converting_From)).check(matches(withText("AZN")));

        onView(withId(R.id.currency_converting_From)).perform(click());
        onView(withText("BMD")).perform(click());
        onView(withId(R.id.currency_converting_From)).check(matches(withText("BMD")));
    }

    @Test
    public void testCurrencyTo() throws Exception {
        onView(withId(R.id.currency_converting_To)).perform(click());
        onView(withText("NGN")).perform(click());
        onView(withId(R.id.currency_converting_To)).check(matches(withText("NGN")));

        onView(withId(R.id.currency_converting_To)).perform(click());
        onView(withText("EUR")).perform(click());
        onView(withId(R.id.currency_converting_To)).check(matches(withText("EUR")));

        onView(withId(R.id.currency_converting_To)).perform(click());
        onView(withText("AFN")).perform(click());
        onView(withId(R.id.currency_converting_To)).check(matches(withText("AFN")));

        onView(withId(R.id.currency_converting_To)).perform(click());
        onView(withText("AZN")).perform(click());
        onView(withId(R.id.currency_converting_To)).check(matches(withText("AZN")));

        onView(withId(R.id.currency_converting_To)).perform(click());
        onView(withText("BMD")).perform(click());
        onView(withId(R.id.currency_converting_To)).check(matches(withText("BMD")));

    }

    @Test
    public void testDeleteButtonClicked() throws Exception {
        onView(withText("CURR")).check(matches(withText("CURR")))
                .perform(click());
        int j;
        String output = "";

        for (int i = 0; i < 5; i++) {
            j = r.nextInt(9);
            output = output + j;
            onView(withText((String.valueOf(j)))).check(matches(withText(String.valueOf(j)))).perform(click());
            onView(withId(R.id.inputTextView)).check(matches(withText(output)));
        }

        for (int i = 0; i < 5; i++) {

            onView(withText("C")).perform(click());
        }

        onView(withId(R.id.inputTextView)).check(matches(withText("")));
    }

}