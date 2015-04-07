package com.urbanaplant.android.urbanpotager.listeners;

import android.bluetooth.BluetoothDevice;

/**
 * Created by Tatiana Grange on 19/03/2015.
 */
public interface OnBluetooth {
    public void onBluetoothDiscovery(BluetoothDevice device);
    public void onBluetoothRead(String s);
}
