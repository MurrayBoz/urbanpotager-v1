package com.urbanaplant.android.urbanpotager.customViews;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;


import android.widget.CheckBox;
import com.urbanaplant.android.urbanpotager.R;
import com.urbanaplant.android.urbanpotager.listeners.OnCheckManageSettings;

import it.gmariotti.cardslib.library.internal.CardExpand;

/**
 * Created by Tatiana Grange on 06/03/15.
 */
public class CustomExpandCardMaintenance extends CardExpand implements CompoundButton.OnCheckedChangeListener {

    private CheckBox cb_lights;
    private CheckBox cb_water;
    private CheckBox cb_cleaning;
    private OnCheckManageSettings listener;

    //Use your resource ID for your inner layout
        public CustomExpandCardMaintenance(Context context) {
        super(context, R.layout.card_expande_maintenance);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        colorDrawableLeft(view,(TextView) view.findViewById(R.id.maintenance_pump_text));
        colorDrawableLeft(view,(TextView) view.findViewById(R.id.maintenance_light_text));
        colorDrawableLeft(view,(TextView) view.findViewById(R.id.maintenance_clean_text));

        cb_lights = (CheckBox) view.findViewById(R.id.maintenance_light_switch);
        cb_lights.setOnCheckedChangeListener(this);
        cb_water = (CheckBox) view.findViewById(R.id.maintenance_pump_switch);
        cb_water.setOnCheckedChangeListener(this);
        cb_cleaning = (CheckBox) view.findViewById(R.id.maintenance_clean_switch);
        cb_cleaning.setOnCheckedChangeListener(this);
    }

    private void colorDrawableLeft(View v, TextView tv) {
        Drawable[] draws = tv.getCompoundDrawables();
        draws[0].setColorFilter(v.getResources().getColor(R.color.secondTextColor), PorterDuff.Mode.SRC_IN);
        tv.setCompoundDrawables(draws[0],draws[1],draws[2],draws[3]);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()){
            case R.id.maintenance_clean_switch:
                cb_lights.setEnabled(!isChecked);
                cb_water.setEnabled(!isChecked);
                if(isChecked){
                    cb_lights.setChecked(!isChecked);
                    cb_water.setChecked(!isChecked);
                }
                listener.onCheckClean(isChecked);
                break;
            case R.id.maintenance_light_switch:
                if(cb_lights.isEnabled())
                    listener.onCheckLight(isChecked);
                break;
            case R.id.maintenance_pump_switch:
                if(cb_water.isEnabled())
                    listener.onCheckPump(isChecked);
                break;
        }
    }

    public void setListener(OnCheckManageSettings listener) {
        this.listener = listener;
    }
}