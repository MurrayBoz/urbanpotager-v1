package com.urbanaplant.android.urbanpotager.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.urbanaplant.android.urbanpotager.MainActivity;
import com.urbanaplant.android.urbanpotager.R;
import com.urbanaplant.android.urbanpotager.communication.BluetoothService;
import com.urbanaplant.android.urbanpotager.communication.Protocol;
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
        tv_state.setText("Connecting to Mypotager by Bluetooth...");

        startService(new Intent(LoadActivity.this, BluetoothService.class));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter(Protocol.BM_BLUETOOTH));

        if(Tools.isDev)
            connect();

    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter(Protocol.BM_BLUETOOTH));
    }
    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int state = intent.getIntExtra("state",Protocol.BM_CONNECT_FAIL);
            if(state == Protocol.BM_CONNECT_SUCCESS) {
                connect();
            }
        }
    };

    private void connect() {
        tv_state.setText("Connected to My Potager!");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Tools.hide(findViewById(R.id.progressBarCircularIndeterminate), 1500, new AnimatorListenerAdapter() {
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
                tv_state.setText("Do some other awesome things...");
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
