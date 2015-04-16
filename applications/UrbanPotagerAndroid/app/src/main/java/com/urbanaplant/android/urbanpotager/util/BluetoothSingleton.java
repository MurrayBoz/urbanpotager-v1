package com.urbanaplant.android.urbanpotager.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.ParcelUuid;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * Created by Tatiana Grange on 19/03/2015.
 */
public class BluetoothSingleton {
//    private static BluetoothSingleton ourInstance = new BluetoothSingleton();
//    private OnBluetooth listener;
//    private BluetoothAdapter bluetoothAdapter;
//    private java.util.UUID UUID;
//    private BluetoothDevice mdevice;
//    private OutputStream outputStream = null;
//    private InputStream inStream;
//    Handler mHandler = new Handler();
//
//    public static BluetoothSingleton getInstance() {
//        return ourInstance;
//    }
//
//    private BluetoothSingleton() {
//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//    }
//
//    public boolean checkBluetoothCompatibility(){
//        return bluetoothAdapter == null;
//    }
//
//    public void connectToUrbanPotager(BluetoothDevice device){
//        mdevice = device;
//        for (ParcelUuid uuid : device.getUuids()) {
//            UUID = uuid.getUuid();
//            AcceptThread at = new AcceptThread();
//            at.run();
//        }
//    }
//
//    public void activate(){
//        if (!bluetoothAdapter.isEnabled()) {
//            bluetoothAdapter.enable();
//        }
//    }
//
//    private Set<BluetoothDevice> getKnowBluetoothDevices(){
//        return bluetoothAdapter.getBondedDevices();
//    }
//
//    public void startDetection(Context c, OnBluetooth listener){
//        this.listener = listener;
//        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        c.registerReceiver(bluetoothReceiver, filter);
//        bluetoothAdapter.startDiscovery();
//    }
//
//    public void stopDetection(Context c){
//        bluetoothAdapter.cancelDiscovery();
//        c.unregisterReceiver(bluetoothReceiver);
//    }
//
//
//    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                listener.onBluetoothDiscovery(device);
//            }
//        }
//    };
//
//    public void write(String s) throws IOException {
//        if(outputStream != null)
//            outputStream.write(s.getBytes());
//    }
//
//    private void read() throws IOException {
//        byte[] buffer = new byte[42];
//        int bytes;
//
//        String end = "\n";
//        StringBuilder curMsg = new StringBuilder();
//        String fullMessage = "";
//
//        while (-1 != (bytes = inStream.read(buffer))) {
//            curMsg.append(new String(buffer, 0, bytes, Charset.forName("UTF-8")));
//            int endIdx = curMsg.indexOf(end);
//            if (endIdx != -1) {
//                fullMessage = curMsg.substring(0, endIdx + end.length());
//                curMsg.delete(0, endIdx + end.length());
//                listener.onBluetoothRead(fullMessage);
//            }
//        }
//    }
//
//    private class AcceptThread extends Thread {
//
//        private final BluetoothSocket mSocket;
//
//        public AcceptThread() {
//            BluetoothSocket tmp = null;
//            try {
//                tmp = mdevice.createRfcommSocketToServiceRecord(UUID);
//            } catch (IOException e) { }
//            mSocket = tmp;
//        }
//
//        public void run() {
//                try {
//                    mSocket.connect();
//                    outputStream = mSocket.getOutputStream();
//                    inStream = mSocket.getInputStream();
//                    startReadDetection();
//                } catch (IOException e) {
//                }
//        }
//
//        private void startReadDetection() {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (true) {
//                        try {
//                            mHandler.postDelayed(new Runnable() {
//
//                                @Override
//                                public void run() {
//                                    try {
//                                        read();
//                                        mHandler.postDelayed(this, 1000);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }, 1000);
//                        } catch (Exception e) {
//                        }
//                    }
//                }
//            }).start();
//        }
//
//        public void cancel() {
//            try {
//                mSocket.close();
//            } catch (IOException e) { }
//        }
//    }

}
