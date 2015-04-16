package com.urbanaplant.android.urbanpotager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.urbanaplant.android.urbanpotager.activities.Settings;
import com.urbanaplant.android.urbanpotager.communication.BluetoothService;
import com.urbanaplant.android.urbanpotager.communication.Protocol;
import com.urbanaplant.android.urbanpotager.fragments.FragmentMyPotager;
import com.urbanaplant.android.urbanpotager.fragments.FragmentHistoric;
import com.urbanaplant.android.urbanpotager.fragments.FragmentSettings;
import com.urbanaplant.android.urbanpotager.listeners.OnFragmentInteractionListener;
import com.urbanaplant.android.urbanpotager.util.Tools;

import java.io.IOException;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;


public class MainActivity extends MaterialNavigationDrawer implements
        MaterialAccountListener,
        View.OnSystemUiVisibilityChangeListener,
        OnFragmentInteractionListener {

    public static boolean active = false;
    private MaterialSection settings;
    private MaterialSection myPotager;
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
        myPotager = newSection("Dashboard", R.drawable.ic_dashboard, FragmentMyPotager.newInstance(this));
        myPotager.setTitle("My Potager");
        historic = newSection("Historic", R.drawable.ic_stats, FragmentHistoric.newInstance(this));

        this.addSection(myPotager);
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
                setSection(myPotager);
            }
        }, 400);
        return false;
    }

    @Override
    public void onAccountOpening(MaterialAccount account) {

    }

    @Override
    public void onChangeAccount(MaterialAccount newAccount) {
        this.setSection(myPotager);
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {
    }

    @Override
    public void onChangeFragment() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter(Protocol.BM_BLUETOOTH));
    }
    @Override
    protected void onStop() {
        active = false;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
//        doUnbindService();
        super.onDestroy();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String datas = intent.getStringExtra("datas");
            datas = datas.substring(0,datas.length()-1);
            Tools.toast(MainActivity.this,datas);
        }
    };

    private BluetoothService mBoundService;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mBoundService = ((BluetoothService.BluetoothBinder)service).getService();
            Tools.log("up");
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mBoundService = null;
            Tools.log("down");
        }
    };

    private boolean mIsBound = false;

    public void doBindService() {
        // Establish a connection with the service. We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        bindService(new Intent(getBaseContext(),
                BluetoothService.class), mConnection, Context.BIND_AUTO_CREATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    mBoundService.write("fevqdw");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 400);

        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }
}