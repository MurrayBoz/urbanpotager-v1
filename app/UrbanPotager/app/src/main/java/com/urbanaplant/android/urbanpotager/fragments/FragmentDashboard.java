package com.urbanaplant.android.urbanpotager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gc.materialdesign.views.ProgressBarDeterminate;
import com.urbanaplant.android.urbanpotager.R;
import com.urbanaplant.android.urbanpotager.customViews.MyFragment;
import com.urbanaplant.android.urbanpotager.listeners.OnFragmentInteractionListener;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Created by Tatiana Grange on 17/02/2015.
 */
public class FragmentDashboard extends MyFragment{

    /* **************************
	 * 		Constructors		*
	 ****************************/
    public FragmentDashboard(){}

    public static FragmentDashboard newInstance(OnFragmentInteractionListener fragmentL)  {
        FragmentDashboard fragment = new FragmentDashboard();
        fragment.setListener(fragmentL);
        return fragment;
    }

    /* **********************
	 * 		Override		*
	 ************************/
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ProgressBarDeterminate pbd = (ProgressBarDeterminate) v.findViewById(R.id.waterLevel);
        pbd.setProgress(75);

        return v;
    }

}