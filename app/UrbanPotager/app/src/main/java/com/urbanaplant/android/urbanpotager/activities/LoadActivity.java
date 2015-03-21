package com.urbanaplant.android.urbanpotager.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.urbanaplant.android.urbanpotager.MainActivity;
import com.urbanaplant.android.urbanpotager.R;
import com.urbanaplant.android.urbanpotager.util.Tools;


public class LoadActivity extends ActionBarActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Tools.hide(findViewById(R.id.progressBarCircularIndeterminate),1500, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        Intent mainIntent = new Intent(LoadActivity.this, MainActivity.class);
                        LoadActivity.this.startActivity(mainIntent);
                        LoadActivity.this.finish();
                    }
                });
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


}
