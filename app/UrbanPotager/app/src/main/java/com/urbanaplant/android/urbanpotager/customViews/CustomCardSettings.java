package com.urbanaplant.android.urbanpotager.customViews;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.urbanaplant.android.urbanpotager.R;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Tatiana Grange on 06/03/2015.
 */
public class CustomCardSettings extends Card {

    private EditText sunrise;
    private EditText sunset;
    private EditText watering;
    private EditText maxTemp;
    private EditText minTemp;
    private EditText minWater;
    private EditText alertWater;
    private View.OnClickListener clickListener;

    public CustomCardSettings(Context context, View.OnClickListener clickListener) {
        super(context, R.layout.content_settings);

        this.clickListener = clickListener;
    }


    @Override
    public void setupInnerViewElements(ViewGroup parent, View v) {
        sunset = (EditText) v.findViewById(R.id.et_sunset);
        colorDrawableLeft(v,sunset);
        sunset.setOnClickListener(clickListener);

        sunrise = (EditText) v.findViewById(R.id.et_sunrise);
        colorDrawableLeft(v,sunrise);
        sunrise.setOnClickListener(clickListener);

        watering = (EditText) v.findViewById(R.id.et_watering);
        colorDrawableLeft(v,watering);
        watering.setOnClickListener(clickListener);

        maxTemp = (EditText) v.findViewById(R.id.et_temp_max);
        colorDrawableLeft(v,maxTemp);

        minTemp = (EditText) v.findViewById(R.id.et_temp_min);
        colorDrawableLeft(v,minTemp);

        minWater = (EditText) v.findViewById(R.id.et_water_min);
        colorDrawableLeft(v,minWater);

        alertWater = (EditText) v.findViewById(R.id.et_water_alert);
        colorDrawableLeft(v,alertWater);
    }

    private void colorDrawableLeft(View v, EditText editText) {
        Drawable[] draws = editText.getCompoundDrawables();
        draws[0].setColorFilter(v.getResources().getColor(R.color.secondTextColor), PorterDuff.Mode.SRC_IN);
        editText.setCompoundDrawables(draws[0],draws[1],draws[2],draws[3]);
    }

    public boolean isHourField(View v){
        return v.getId() == sunrise.getId() || v.getId() == sunset.getId();
    }

    public boolean isWatering(View v) {
        return v.getId() == watering.getId();
    }
}