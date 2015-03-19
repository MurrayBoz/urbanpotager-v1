package com.urbanaplant.android.urbanpotager.customViews;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.urbanaplant.android.urbanpotager.R;

import it.gmariotti.cardslib.library.internal.CardExpand;

/**
 * Created by Tatiana Grange on 06/03/15.
 */
public class CustomExpandCardMaintenance extends CardExpand {

    //Use your resource ID for your inner layout
    public CustomExpandCardMaintenance(Context context) {
        super(context, R.layout.card_expande_maintenance);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        colorDrawableLeft(view,(TextView) view.findViewById(R.id.maintenance_pump_text));
        colorDrawableLeft(view,(TextView) view.findViewById(R.id.maintenance_light_text));
        colorDrawableLeft(view,(TextView) view.findViewById(R.id.maintenance_clean_text));
    }

    private void colorDrawableLeft(View v, TextView tv) {
        Drawable[] draws = tv.getCompoundDrawables();
        draws[0].setColorFilter(v.getResources().getColor(R.color.secondTextColor), PorterDuff.Mode.SRC_IN);
        tv.setCompoundDrawables(draws[0],draws[1],draws[2],draws[3]);
    }
}