package com.andela.currencycalculator.model.helper;

/**
 * Created by Spykins on 25/02/2016.
 */
public class SpinnerCurrency {
    String spinnerCodeText;
    int imageId;
    String currencyCountry;

    public SpinnerCurrency(int imageId, String spinnerCodeText, String currencyCountry) {
        this.imageId = imageId;
        this.spinnerCodeText = spinnerCodeText;
        this.currencyCountry = currencyCountry;
    }

    public int getImageId() {
        return imageId;
    }

    public String getSpinnerCodeText() {
        return spinnerCodeText;
    }

    public String getCurrencyCountry() {
        return currencyCountry;
    }
}
