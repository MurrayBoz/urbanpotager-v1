package com.urbanaplant.android.urbanpotager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

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
    private final int btn_notif = 100;

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
        MaterialAccount account = new MaterialAccount(this.getResources(),"At Work","Water for 3 weeks",R.drawable.ic_home, R.drawable.wallpaper1);
        this.addAccount(account);

        MaterialAccount account2 = new MaterialAccount(this.getResources(),"At Home","Need water",R.drawable.ic_work,R.drawable.wallpaper3);
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
        this.addBottomSection(newSection("App Settings (si besoin?)",R.drawable.ic_app_settings,new Intent(this,Settings.class)));


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(0, btn_notif, Menu.NONE, "Notifications").setIcon(R.drawable.ic_notif_one).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setSection(dashboard);
            }
        }, 400);


        return false;
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