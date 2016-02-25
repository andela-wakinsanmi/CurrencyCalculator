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

    public View getView(int position, View view, ViewGroup parent){
        SpinnerViewHolder spinnerViewHolder;

        if(view == null){
            spinnerViewHolder = new SpinnerViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(groupId,parent,false);
            spinnerViewHolder.imageView = (ImageView) view.findViewById(R.id.spinnerImageView);
            spinnerViewHolder.textView = (TextView)view.findViewById(R.id.spinnerCodeView);
            view.setTag(spinnerViewHolder);
        } else {
            spinnerViewHolder = (SpinnerViewHolder) view.getTag();
        }

        if (spinnerCurrencies.get(position) != null){
            spinnerViewHolder.imageView.setImageResource(spinnerCurrencies.get(position).getImageId());
            spinnerViewHolder.textView.setText(spinnerCurrencies.get(position).getSpinnerCodeText());
        }

        return view;
    }

    public View getDropDownView(int position, View view,ViewGroup parent){
        return getView(position,view,parent);
    }

    class SpinnerViewHolder{
        ImageView imageView;
        TextView textView;

    }
}
