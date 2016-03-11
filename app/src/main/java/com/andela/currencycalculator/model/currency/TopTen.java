package com.andela.currencycalculator.model.currency;

import java.util.ArrayList;

/**
 * Created by Spykins on 04/03/2016.
 */
public class  TopTen {
    private ArrayList<String> topTen;

    public TopTen() {
        topTen = new ArrayList<>();

        for (TopTenList topTenList : TopTenList.values()){
            topTen.add(topTenList.name());
        }
    }

    public ArrayList<String> getTopTen(){
        return topTen;
    }


}
