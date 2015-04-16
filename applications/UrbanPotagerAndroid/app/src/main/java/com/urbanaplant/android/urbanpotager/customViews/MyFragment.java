package com.urbanaplant.android.urbanpotager.customViews;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.urbanaplant.android.urbanpotager.listeners.OnFragmentInteractionListener;
/**
 * All Fragment extends from this Fragment.
 * All Fragment specifications are here.
 * This OnFragmentInteractionListener come from https://developer.android.com/training/basics/fragments/communicating.html
 */
public abstract class MyFragment extends Fragment {

    /* **********************
     * 		Attributes		*
     ************************/
    protected OnFragmentInteractionListener mListener;

    /* **********************
     * 		Accessors		*
     ************************/
    public void setListener(OnFragmentInteractionListener listener) {
        mListener = listener;
    }

    /* **************************
	 * 		Constructors		*
	 ****************************/
    public MyFragment() {
        // Required empty public constructor
    }

    /* **********************
	 * 		Override		*
	 ************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View decorView = getActivity().getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(
                new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int i) {
                    }
                });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}