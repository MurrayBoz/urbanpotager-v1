package com.urbanaplant.android.urbanpotager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.views.ProgressBarDeterminate;
import com.urbanaplant.android.urbanpotager.R;
import com.urbanaplant.android.urbanpotager.customViews.MyFragment;
import com.urbanaplant.android.urbanpotager.listeners.OnFragmentInteractionListener;

/**
 * Created by Tatiana Grange on 17/02/2015.
 */
public class FragmentMyPotager extends MyFragment{

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

        return v;
    }

}