package com.urbanaplant.android.urbanpotager.fragments;

import android.os.Bundle;
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

        tv_temperatureValue = (TextView) v.findViewById(R.id.tv_temperatureValue);
        tv_humidityValue = (TextView) v.findViewById(R.id.tv_humidityValue);
        tv_lightValue = (TextView) v.findViewById(R.id.tv_lightValue);
        airIcon = (ImageView) v.findViewById(R.id.iv_airIcon);

        btn_update = (ButtonFlat) v.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btn_update.getId()){
            ((MainActivity)getActivity()).write(Protocol.ProtoWrite.UPDATE_INFORMATIONS);
        }
    }

    public void setDatas(String commandDatas) {
        String[] splitString = commandDatas.split("/");
        tv_temperatureValue.setText(splitString[1] + "°");
        tv_humidityValue.setText(splitString[2] + "%");
        tv_lightValue.setText(splitString[0] + "%");
    }
}