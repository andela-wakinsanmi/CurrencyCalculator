package com.andela.currencycalculator.model.currency;

import java.util.ArrayList;

/**
 * Created by Spykins on 04/03/2016.
 */
public class  TopTen {
    private ArrayList<String> topTen;

    private enum MyList{
        NGN,USD,KWD, BHD,OMR, GBP,EUR,CHF,LYD,BND,SGD,AUD
    }

    public TopTen() {
        topTen = new ArrayList<>();

        for (MyList myList : MyList.values()){
            topTen.add(myList.name());
        }
    }

    public ArrayList<String> getTopTen(){
        return topTen;
    }


}
