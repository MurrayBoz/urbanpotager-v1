package com.urbanaplant.android.urbanpotager.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.Switch;
import com.urbanaplant.android.urbanpotager.MainActivity;
import com.urbanaplant.android.urbanpotager.R;
import com.urbanaplant.android.urbanpotager.SplashActivity;
import com.urbanaplant.android.urbanpotager.customViews.CustomCardSettings;
import com.urbanaplant.android.urbanpotager.customViews.CustomExpandCardMaintenance;
import com.urbanaplant.android.urbanpotager.customViews.CustomHeader;
import com.urbanaplant.android.urbanpotager.customViews.CustomHeaderSwitch;
import com.urbanaplant.android.urbanpotager.customViews.MyFragment;
import com.urbanaplant.android.urbanpotager.listeners.OnFragmentInteractionListener;
import com.urbanaplant.android.urbanpotager.util.Tools;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.view.CardViewNative;


public class FragmentSettings extends MyFragment implements View.OnClickListener, Switch.OnCheckListener  {

    private Card cardMaintenance;
    private CustomCardSettings cardSettings;

    /* **************************
     * 		Constructors		*
     ****************************/
    public FragmentSettings(){}

    public static FragmentSettings newInstance(OnFragmentInteractionListener fragmentL)  {
        FragmentSettings fragment = new FragmentSettings();
        fragment.setListener(fragmentL);
        return fragment;
    }

    /* **********************
	 * 		Override		*
	 ************************/

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        Context context = getActivity().getBaseContext();
        cardMaintenance = new Card(context);
        CardHeader header = new CustomHeaderSwitch(context,this);
        cardMaintenance.addCardHeader(header);
        cardMaintenance.setTitle("Le mode maintenance vous permet de controller votre UrbanPotager en direct.");
        CardExpand expand = new CustomExpandCardMaintenance(context);
        cardMaintenance.addCardExpand(expand);
        ViewToClickToExpand viewToClickToExpand = ViewToClickToExpand.builder().enableForExpandAction();
        cardMaintenance.setViewToClickToExpand(viewToClickToExpand);
        CardViewNative cardView = (CardViewNative) v.findViewById(R.id.card_maintenance);
        cardView.setCard(cardMaintenance);

        cardSettings = new CustomCardSettings(context,this);
        CardHeader header2 = new CustomHeader(context,"Settings");
        cardSettings.addCardHeader(header2);
        CardViewNative cardView2 = (CardViewNative) v.findViewById(R.id.card_settings);
        cardView2.setCard(cardSettings);

        return v;
    }


    @Override
    public void onClick(final View v) {
        if(cardSettings.isHourField(v)) {
            int hour = v.getId() == R.id.et_sunrise ? 8 : 22;
            int minute = 00;

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(
                    getActivity(),
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            ((EditText) v).setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                        }
                    },
                    hour,
                    minute,
                    true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }else if(cardSettings.isWatering(v)){
            new MaterialDialog.Builder(getActivity())
                    .title("Choose watering settings")
                    .items(R.array.items)
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            ((EditText)v).setText(text);
                        }
                    })
                    .positiveText("Ok")
                    .show();
        }
    }

    @Override
    public void onCheck(boolean b) {
        cardMaintenance.doToogleExpand();

        if(b) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ScrollView sv = (ScrollView) getActivity().findViewById(R.id.scrollView);
                    sv.smoothScrollTo(0, sv.getBottom());
                }
            }, 400);
        }
    }
}