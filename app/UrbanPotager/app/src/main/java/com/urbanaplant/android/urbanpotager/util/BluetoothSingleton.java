package com.urbanaplant.android.urbanpotager.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.urbanaplant.android.urbanpotager.listeners.OnBluetoothDiscovery;

import java.util.Set;

/**
 * Created by Tatiana Grange on 19/03/2015.
 */
public class BluetoothSingleton {
    private static BluetoothSingleton ourInstance = new BluetoothSingleton();
    private OnBluetoothDiscovery listener;
    private BluetoothAdapter bluetoothAdapter;

    public static BluetoothSingleton getInstance() {
        return ourInstance;
    }

    private BluetoothSingleton() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public boolean checkBluetoothCompatibility(){
        return bluetoothAdapter == null;
    }

    public void activate(){
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
    }

    public Set<BluetoothDevice> getKnowBluetoothDevices(){
        return bluetoothAdapter.getBondedDevices();
    }

    public void startDetection(Context c, OnBluetoothDiscovery listener){
        this.listener = listener;
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        c.registerReceiver(bluetoothReceiver, filter);
        bluetoothAdapter.startDiscovery();
    }

    public void stopDetection(Context c){
        bluetoothAdapter.cancelDiscovery();
        c.unregisterReceiver(bluetoothReceiver);
    }


    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                listener.onBluetoothDiscovery(device);
            }
        }
    };
}
