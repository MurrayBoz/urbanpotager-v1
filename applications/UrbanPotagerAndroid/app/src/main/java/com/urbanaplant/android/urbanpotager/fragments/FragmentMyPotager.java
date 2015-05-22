package com.urbanaplant.android.urbanpotager.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.Button;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ProgressBarDeterminate;
import com.urbanaplant.android.urbanpotager.MainActivity;
import com.urbanaplant.android.urbanpotager.R;
import com.urbanaplant.android.urbanpotager.communication.BluetoothService;
import com.urbanaplant.android.urbanpotager.communication.Protocol;
import com.urbanaplant.android.urbanpotager.customViews.MyFragment;
import com.urbanaplant.android.urbanpotager.listeners.OnFragmentInteractionListener;
import com.urbanaplant.android.urbanpotager.util.Tools;

/**
 * Created by Tatiana Grange on 17/02/2015.
 */
public class FragmentMyPotager extends MyFragment implements View.OnClickListener {

    private TextView tv_temperatureValue;
    private TextView tv_humidityValue;
    private TextView tv_lightValue;
    private ImageView airIcon;
    private ButtonFlat btn_update;
    private TextView tv_nextWatering;
    private TextView tv_isLightOn;
    private TextView tv_lastSync;

    /* **************************
         * 		Constructors		*
         ****************************/
    public FragmentMyPotager(){}

    public static FragmentMyPotager newInstance(OnFragmentInteractionListener fragmentL)  {
        FragmentMyPotager fragment = new FragmentMyPotager();
        fragment.setListener(fragmentL);
        return fragment;
    }

    /* **********************
	 * 		Override		*
	 ************************/
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_potager, container, false);

        ProgressBarDeterminate pbd = (ProgressBarDeterminate) v.findViewById(R.id.waterLevel);
        pbd.setProgress(75);

        tv_lastSync = (TextView) v.findViewById(R.id.tv_lastSync);
        tv_temperatureValue = (TextView) v.findViewById(R.id.tv_temperatureValue);
        tv_humidityValue = (TextView) v.findViewById(R.id.tv_humidityValue);
        tv_lightValue = (TextView) v.findViewById(R.id.tv_lightValue);
        tv_nextWatering = (TextView) v.findViewById(R.id.tv_nextWatering);
        tv_isLightOn = (TextView) v.findViewById(R.id.tv_isLightOn);
        airIcon = (ImageView) v.findViewById(R.id.iv_airIcon);

        btn_update = (ButtonFlat) v.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);

        updateInformations();

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btn_update.getId()){
            ((MainActivity)getActivity()).write(Protocol.ProtoWrite.UPDATE_INFORMATIONS);
        }
    }

    public void updateInformations() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        tv_lastSync.setText(prefs.getString("lastSync",""));
        tv_temperatureValue.setText(prefs.getString("temperature","25°"));
        tv_humidityValue.setText(prefs.getString("humidity","50%"));
        tv_lightValue.setText(prefs.getString("light","75%°"));
        tv_nextWatering.setText(prefs.getString("nextWatering","10mn"));
        tv_isLightOn.setText(prefs.getString("isLightOn","ON"));
    }

    public void updateLight() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        tv_lightValue.setText(prefs.getString("light","75%°"));
    }
}