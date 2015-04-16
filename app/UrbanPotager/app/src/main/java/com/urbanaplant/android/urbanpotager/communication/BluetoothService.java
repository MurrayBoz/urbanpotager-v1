package com.urbanaplant.android.urbanpotager.communication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.urbanaplant.android.urbanpotager.MainActivity;
import com.urbanaplant.android.urbanpotager.R;
import com.urbanaplant.android.urbanpotager.util.Tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Created by Tatiana Grange on 07/04/2015.
 */
public class BluetoothService extends Service{
//    private final IBinder mBinder = new BluetoothBinder();
    private String NAME = "MyPotager";
    private BluetoothSocket mSocket;
    private BluetoothAdapter bluetoothAdapter;
    private java.util.UUID UUID;
    private BluetoothDevice mdevice;
    private OutputStream outputStream = null;
    private InputStream inStream;
    private ConnectTask ct;

//    public class BluetoothBinder extends Binder {
//        public BluetoothService getService() {
//            return BluetoothService.this;
//        }
//    }

    @Override
    public IBinder onBind(Intent intent) {
//        return mBinder;
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startDetection();

        //stop detection in 10 sec
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopDetection();
            }
        }, 10000);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void write(String s) throws IOException {
        if(outputStream != null)
            outputStream.write(s.getBytes());
    }

    private String read() throws IOException {
        byte[] buffer = new byte[42];
        int bytes;

        String end = "\n";
        StringBuilder curMsg = new StringBuilder();

        Tools.log("Dans read");
        if(inStream.available() > 0) {
            while (-1 != (bytes = inStream.read(buffer))) {
                curMsg.append(new String(buffer, 0, bytes, Charset.forName("UTF-8")));
                Tools.log(curMsg.toString());
                int endIdx = curMsg.indexOf(end);
                if (endIdx != -1) {
                    String fullMessage = curMsg.substring(0, endIdx + end.length());
                    curMsg.delete(0, endIdx + end.length());
                    Tools.log(fullMessage);
                    return fullMessage;
                }
            }
        }

        Tools.log("Après le while");
        return null;
    }

    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device.getName().equals(NAME)){
                    connectToUrbanPotager(device);
                }
            }
        }
    };

    private void startDetection(){
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(bluetoothReceiver, filter);
        bluetoothAdapter.startDiscovery();
    }

    private void stopDetection(){
        bluetoothAdapter.cancelDiscovery();
        try{
            unregisterReceiver(bluetoothReceiver);
        }catch (IllegalArgumentException iae){
            Tools.log("Already unregistered");
        }
    }

    private void connectToUrbanPotager(BluetoothDevice device){
        mdevice = device;
        for (ParcelUuid uuid : device.getUuids()) {
            UUID = uuid.getUuid();
            ct = new ConnectTask();
            ct.execute();
        }
    }
    private class ConnectTask extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            BluetoothSocket tmp = null;
            try {
                tmp = mdevice.createRfcommSocketToServiceRecord(UUID);
            } catch (IOException e) { }
            mSocket = tmp;
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            if(result){
                Intent intent = new Intent(Protocol.BM_BLUETOOTH);
                intent.putExtra("state", Protocol.BM_CONNECT_SUCCESS);
                LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                mSocket.connect();
                outputStream = mSocket.getOutputStream();
                inStream = mSocket.getInputStream();
                (new ReadTask()).execute();
                return true;
            } catch (IOException e) {
            }
            return null;
        }
    }

    private class ReadTask extends AsyncTask<Void, Integer, Boolean> {
        String readBT = null;
        @Override
        protected void onPreExecute() {}

        @Override
        protected void onPostExecute(final Boolean result) {
            if(result && readBT != null){
                if(MainActivity.active){
                    Intent intent = new Intent(Protocol.BM_BLUETOOTH);
                    intent.putExtra("datas", readBT);
                    LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
                    readBT = null;
                }else {
                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(getBaseContext())
                                    .setSmallIcon(R.drawable.ic_dashboard)
                                    .setContentTitle("My Notification Title")
                                    .setContentText("Something interesting happened");
                    int NOTIFICATION_ID = 12345;

                    Intent targetIntent = new Intent(getBaseContext(), MainActivity.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(), 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(contentIntent);
                    NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    nManager.notify(NOTIFICATION_ID, builder.build());
                }
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    (new ReadTask()).execute();
                }
            }, 1000);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                readBT = read();
                return true;
            } catch (IOException e) {
            }
            return null;
        }
    }
}
