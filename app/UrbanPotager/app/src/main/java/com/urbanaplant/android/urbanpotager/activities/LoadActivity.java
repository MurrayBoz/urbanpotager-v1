package com.urbanaplant.android.urbanpotager.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.urbanaplant.android.urbanpotager.MainActivity;
import com.urbanaplant.android.urbanpotager.R;
import com.urbanaplant.android.urbanpotager.util.Tools;


public class LoadActivity extends ActionBarActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    private TextView tv_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        String versionName = null;
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ((TextView)findViewById(R.id.tv_version)).setText(versionName);
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_state.setText("Connecting to the server...");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Tools.hide(findViewById(R.id.progressBarCircularIndeterminate),1500, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        tv_state.setText("");
                        Intent mainIntent = new Intent(LoadActivity.this, MainActivity.class);
                        LoadActivity.this.startActivity(mainIntent);
                        LoadActivity.this.finish();
                    }
                });
            }
        }, SPLASH_DISPLAY_LENGTH);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_state.setText("Retrieving data from your garden...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_state.setText("It is almost done!");

                    }
                }, SPLASH_DISPLAY_LENGTH/2);
            }
        }, SPLASH_DISPLAY_LENGTH*2/3);

    }


}
