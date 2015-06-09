package com.urbanaplant.android.urbanpotager.customViews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gc.materialdesign.views.Switch;
import com.urbanaplant.android.urbanpotager.R;

import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by Tatiana Grange on 06/03/2015.
 */
public class CustomHeaderSwitch extends CardHeader {

    private Switch.OnCheckListener checkListener;

    private Switch swicth;

    public CustomHeaderSwitch(Context context) {
        super(context, R.layout.card_header_swicth);
    }

    public CustomHeaderSwitch(Context context, Switch.OnCheckListener checkListener) {
        super(context, R.layout.card_header_swicth);
        this.checkListener = checkListener;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        if (view!=null){
            TextView t2 = (TextView) view.findViewById(R.id.title);
            if (t2!=null)
                t2.setText("Maintenance Mode");

            swicth = (Switch) view.findViewById(R.id.switchView);
            if(swicth != null)
                swicth.setOncheckListener(checkListener);
        }
    }
}