package com.urbanaplant.android.urbanpotager.customViews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.urbanaplant.android.urbanpotager.R;

import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by Tatiana Grange on 06/03/2015.
 */
public class CustomHeader extends CardHeader {

    private String title;

    public CustomHeader(Context context) {
        super(context, R.layout.card_header);
    }

    public CustomHeader(Context context, String title) {
        super(context, R.layout.card_header);
        this.title = title;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        if (view!=null){
            TextView t2 = (TextView) view.findViewById(R.id.title);
            if (t2!=null) {
                t2.setText(this.title);
            }

        }
    }
}