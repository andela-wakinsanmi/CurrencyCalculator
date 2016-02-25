package com.andela.currencycalculator.model.helper;

/**
 * Created by Spykins on 25/02/2016.
 */
public class SpinnerCurrency {
    String spinnerCodeText;
    int imageId;

    public SpinnerCurrency(int imageId, String spinnerCodeText) {
        this.imageId = imageId;
        this.spinnerCodeText = spinnerCodeText;
    }

    public int getImageId() {
        return imageId;
    }

    public String getSpinnerCodeText() {
        return spinnerCodeText;
    }
}
