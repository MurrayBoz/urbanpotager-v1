package com.urbanaplant.android.urbanpotager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.urbanaplant.android.urbanpotager.activities.LoadActivity;
import com.urbanaplant.android.urbanpotager.activities.Settings;
import com.urbanaplant.android.urbanpotager.communication.BluetoothService;
import com.urbanaplant.android.urbanpotager.util.Tools;


public class MainActivityFrienly extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_frienly);

        setTitle("Home Garden");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity_frienly, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            this.startActivityForResult(new Intent(this,Settings.class),Tools.SETTINGS_REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Tools.SETTINGS_REQUEST_CODE && resultCode == Tools.SETTINGS_RESULT_QUIT){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            Intent mainIntent = new Intent(MainActivityFrienly.this, !preferences.getString("layout","1").equals("1") ?  MainActivity.class : MainActivityFrienly.class);
            MainActivityFrienly.this.startActivity(mainIntent);
            MainActivityFrienly.this.finish();
        }
    }
}
