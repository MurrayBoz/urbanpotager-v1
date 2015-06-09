package com.urbanaplant.android.urbanpotager.fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.Switch;
import com.urbanaplant.android.urbanpotager.MainActivity;
import com.urbanaplant.android.urbanpotager.R;
import com.urbanaplant.android.urbanpotager.communication.Protocol;
import com.urbanaplant.android.urbanpotager.customViews.CustomCardSettings;
import com.urbanaplant.android.urbanpotager.customViews.CustomExpandCardMaintenance;
import com.urbanaplant.android.urbanpotager.customViews.CustomHeader;
import com.urbanaplant.android.urbanpotager.customViews.CustomHeaderSwitch;
import com.urbanaplant.android.urbanpotager.customViews.MyFragment;
import com.urbanaplant.android.urbanpotager.listeners.OnCheckManageSettings;
import com.urbanaplant.android.urbanpotager.listeners.OnFragmentInteractionListener;
import com.urbanaplant.android.urbanpotager.util.Tools;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.view.CardViewNative;


public class FragmentSettings extends MyFragment implements View.OnClickListener, Switch.OnCheckListener, OnCheckManageSettings {

    private Card cardMaintenance;
    private CustomCardSettings cardSettings;
    private CustomHeaderSwitch header;

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
        header = new CustomHeaderSwitch(context,this);
        cardMaintenance.addCardHeader(header);
        cardMaintenance.setTitle("Maintenance Mode allows you to control manually your UrbanPotager.");
        CardExpand expand = new CustomExpandCardMaintenance(context);
        ((CustomExpandCardMaintenance)expand).setListener(this);
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
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        int hour = preferences.getInt("sunriseHour",8);
        int minute = preferences.getInt("sunriseMin",0);
        ((EditText)v.findViewById(R.id.et_sunrise)).setText(String.format("%02d", hour) + ":" + String.format("%02d", minute));

        hour = preferences.getInt("sunsetHour",22);
        minute = preferences.getInt("sunsetMin",0);
        ((EditText)v.findViewById(R.id.et_sunset)).setText(String.format("%02d", hour) + ":" + String.format("%02d", minute));

        String[] array = getResources().getStringArray(R.array.items);
        int which = preferences.getInt("watering", 0);
        ((EditText)v.findViewById(R.id.et_watering)).setText(array[which]);
    }

    @Override
    public void onClick(final View v) {
        if(cardSettings.isHourField(v)) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            int hour = v.getId() == R.id.et_sunrise ? preferences.getInt("sunriseHour",8) : preferences.getInt("sunsetHour",22);
            int minute = v.getId() == R.id.et_sunrise ? preferences.getInt("sunriseMin",0) : preferences.getInt("sunsetMin",0);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(
                    getActivity(),
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            ((EditText) v).setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor editor = preferences.edit();
                            String s = ((EditText) v).getText().toString();
                            editor.putInt(v.getId() == R.id.et_sunrise ? "sunriseHour" : "sunsetHour", hourOfDay);
                            editor.putInt(v.getId() == R.id.et_sunrise ? "sunriseMin" : "sunsetMin", minute);
                            editor.commit();
                            ((MainActivity)getActivity()).writeSet(v.getId() == R.id.et_sunrise ? Protocol.ProtoWriteSet.TIME_SUNRISE : Protocol.ProtoWriteSet.TIME_SUNSET, s);
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

                            String toSend = null;
                            switch (which){
                                case 0:
                                    toSend = "10/10";
                                    break;
                                case 1:
                                    toSend = "20/10";
                                    break;
                                case 2:
                                    toSend = "10/20";
                                    break;
                                case 3:
                                    toSend = "20/20";
                                    break;
                            }

                            if(toSend != null) {
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("watering", which);
                                editor.commit();
                                ((MainActivity) getActivity()).writeSet(Protocol.ProtoWriteSet.WATERING, toSend);
                            }
                        }
                    })
                    .positiveText("Ok")
                    .show();
        }else{
            Tools.toast(getActivity(), "Work in progress");
        }
    }

    @Override
    public void onCheck(boolean b) {
        if(b) {
            cardMaintenance.doExpand();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ScrollView sv = (ScrollView) getActivity().findViewById(R.id.scrollView);
                    sv.smoothScrollTo(0, sv.getBottom());
                }
            }, 400);
        }
        else
            cardMaintenance.doCollapse();

        ((MainActivity)getActivity()).write(b ? Protocol.ProtoWrite.MANAGE_MODE_ON : Protocol.ProtoWrite.MANAGE_MODE_OFF);
    }

    @Override
    public void onCheckLight(boolean isCheck) {
        ((MainActivity)getActivity()).write(isCheck ? Protocol.ProtoWrite.LIGHT_ON : Protocol.ProtoWrite.LIGHT_OFF);
    }

    @Override
    public void onCheckPump(boolean isCheck) {
        ((MainActivity)getActivity()).write(isCheck ? Protocol.ProtoWrite.PUMP_ON : Protocol.ProtoWrite.PUMP_OFF);
    }

    @Override
    public void onCheckClean(boolean isCheck) {
        ((MainActivity)getActivity()).write(isCheck ? Protocol.ProtoWrite.CLEAN_ON : Protocol.ProtoWrite.CLEAN_OFF);
    }
}