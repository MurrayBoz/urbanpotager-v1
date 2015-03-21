package com.urbanaplant.android.urbanpotager.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.urbanaplant.android.urbanpotager.R;

public class LoginActivity extends ActionBarActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        colorDrawableLeft((Button) findViewById(R.id.googlePlus), Color.WHITE);
        colorDrawableLeft((Button) findViewById(R.id.facebook), Color.WHITE);
        colorDrawableLeft((Button) findViewById(R.id.connectAccount), Color.WHITE);
        colorDrawableLeft((Button) findViewById(R.id.createAccount), getResources().getColor(R.color.colorPrimary));

        findViewById(R.id.connectAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherIntent = new Intent(LoginActivity.this, LoadActivity.class);
                LoginActivity.this.startActivity(otherIntent);
                LoginActivity.this.finish();
            }
        });
    }

    private void colorDrawableLeft(Button button, int color) {
        Drawable[] draws = button.getCompoundDrawables();
        draws[0].setColorFilter(color, PorterDuff.Mode.SRC_IN);
        button.setCompoundDrawables(draws[0],draws[1],draws[2],draws[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View currentFocus = this.getCurrentFocus();
        if(currentFocus != null)
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        return true;
    }
}
