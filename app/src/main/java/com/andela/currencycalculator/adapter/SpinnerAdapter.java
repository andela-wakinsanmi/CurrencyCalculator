package com.andela.currencycalculator.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andela.currencycalculator.R;
import com.andela.currencycalculator.model.helper.SpinnerCurrency;

import java.util.ArrayList;

/**
 * Created by Spykins on 25/02/2016.
 */

/**
 *A spinner adapter allows to define two different views: one that shows
 * the data in the spinner itself and one that shows the data in the drop
 * down list when the spinner is pressed.
 * {@link SpinnerAdapter#getDropDownView(int, View, ViewGroup)} Gets a View
 * that displays in the drop down popup the data at the specified position in the data set.
 * {@link SpinnerAdapter#getView(int, View, ViewGroup)} Get a View that displays the data
 * at the specified position in the data set
 *
 */
public class SpinnerAdapter extends ArrayAdapter<SpinnerCurrency> {
    private ArrayList<SpinnerCurrency> spinnerCurrencies;
    private int groupId;
    private Context context;

    public SpinnerAdapter(Context context, int resource, int textViewResourceId, ArrayList<SpinnerCurrency> objects) {
        super(context, resource, textViewResourceId, objects);
        this.spinnerCurrencies = objects;
        this.groupId = resource;
        this.context = context;
    }
    /*

     */

    public View getView(int position, View view, ViewGroup parent){
        SpinnerViewHolder spinnerViewHolder;

        if(view == null){
            spinnerViewHolder = new SpinnerViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(groupId,parent,false);
            spinnerViewHolder.imageView = (ImageView) view.findViewById(R.id.spinnerImageView);
            spinnerViewHolder.currencyCodeTextView = (TextView)view.findViewById(R.id.spinnerCodeView);
            spinnerViewHolder.currencyCountryTextView = (TextView)view.findViewById((R.id.spinnerCountryView));
            view.setTag(spinnerViewHolder);
        } else {
            spinnerViewHolder = (SpinnerViewHolder) view.getTag();
        }

        if (spinnerCurrencies.get(position) != null){
            spinnerViewHolder.imageView.setImageResource(spinnerCurrencies.get(position).getImageId());
            spinnerViewHolder.currencyCodeTextView.setText(spinnerCurrencies.get(position).getSpinnerCodeText());
            spinnerViewHolder.currencyCountryTextView.setText(spinnerCurrencies.get(position).getCurrencyCountry());
        }

        return view;
    }

    public View getDropDownView(int position, View view,ViewGroup parent){
        return getView(position,view,parent);
    }

    class SpinnerViewHolder{
        ImageView imageView;
        TextView currencyCodeTextView;
        TextView currencyCountryTextView;

    }
}
