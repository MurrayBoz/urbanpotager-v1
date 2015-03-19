package com.urbanaplant.android.urbanpotager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.urbanaplant.android.urbanpotager.activities.Settings;
import com.urbanaplant.android.urbanpotager.fragments.FragmentDashboard;
import com.urbanaplant.android.urbanpotager.fragments.FragmentHistoric;
import com.urbanaplant.android.urbanpotager.fragments.FragmentSettings;
import com.urbanaplant.android.urbanpotager.listeners.OnFragmentInteractionListener;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;


public class MainActivity extends MaterialNavigationDrawer implements MaterialAccountListener, View.OnSystemUiVisibilityChangeListener, OnFragmentInteractionListener {

    private MaterialSection settings;
    private MaterialSection dashboard;
    private MaterialSection historic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(this);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setBackPattern(MaterialNavigationDrawer.BACKPATTERN_BACK_TO_FIRST);
        addMultiPaneSupport();
        allowArrowAnimation();

        // add accounts
        MaterialAccount account = new MaterialAccount(this.getResources(),"Au travail","3 semaines d'autonomie",R.drawable.photo, R.drawable.wallpaper1);
        this.addAccount(account);

        MaterialAccount account2 = new MaterialAccount(this.getResources(),"Chez moi","À besoin d'eau",R.drawable.wallpaper2,R.drawable.wallpaper3);
        this.addAccount(account2);

        // set listener
        this.setAccountListener(this);

        // create sections
        settings = newSection("Settings", R.drawable.ic_settings_black_24dp, FragmentSettings.newInstance(this));
        dashboard = newSection("Dashboard", R.drawable.ic_dashboard, FragmentDashboard.newInstance(this));
        dashboard.setTitle("My Potager");
        historic = newSection("Historic", R.drawable.ic_stats, FragmentHistoric.newInstance(this));

        this.addSection(dashboard);
        this.addSection(historic);
        this.addSection(settings);


        // create bottom section
        this.addBottomSection(newSection("App Settings (si besoin?)",R.drawable.ic_settings_black_24dp,new Intent(this,Settings.class)));
    }

    @Override
    public void onAccountOpening(MaterialAccount account) {

    }

    @Override
    public void onChangeAccount(MaterialAccount newAccount) {
        this.setSection(dashboard);
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {
    }

    @Override
    public void onChangeFragment() {

    }
}