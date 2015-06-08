package com.urbanaplant.android.urbanpotager.communication;

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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.urbanaplant.android.urbanpotager.MainActivity;
import com.urbanaplant.android.urbanpotager.R;
import com.urbanaplant.android.urbanpotager.fragments.FragmentMyPotager;
import com.urbanaplant.android.urbanpotager.util.Tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tatiana Grange on 07/04/2015.
 */
public class BluetoothService extends Service{
    private final IBinder mBinder = new BluetoothBinder();
    //private String NAME = "MyPotager";
    private String NAME = "HC-06";
    private BluetoothSocket mSocket;
    private BluetoothAdapter bluetoothAdapter;
    private java.util.UUID UUID;
    private BluetoothDevice mdevice;
    private OutputStream outputStream = null;
    private InputStream inStream;
    private ConnectTask ct;

    public class BluetoothBinder extends Binder {
        public BluetoothService getService() {
            return BluetoothService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
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


    public void write(Protocol.ProtoWrite s) throws IOException {

        if (outputStream != null){
            String toSend = "mas";
            switch (s) {
                case MANAGE_MODE_OFF:
                    toSend += "manof";
                    break;
                case MANAGE_MODE_ON:
                    toSend += "manon";
                    break;
                case PUMP_OFF:
                    toSend += "pumof";
                    break;
                case PUMP_ON:
                    toSend += "pumon";
                    break;
                case LIGHT_OFF:
                    toSend += "ligof";
                    break;
                case LIGHT_ON:
                    toSend += "ligon";
                    break;
                case CLEAN_OFF:
                    toSend += "cleof";
                    break;
                case CLEAN_ON:
                    toSend += "cleon";
                    break;
                case UPDATE_INFORMATIONS:
                    toSend += "upd";
                    break;
            }

            outputStream.write(toSend.getBytes());
        }

    }

    private String read() throws IOException {
        byte[] buffer = new byte[42];
        int bytes;

        String end = "\n";
        StringBuilder curMsg = new StringBuilder();

        if(inStream.available() > 0) {
            while (-1 != (bytes = inStream.read(buffer))) {
                curMsg.append(new String(buffer, 0, bytes, Charset.forName("UTF-8")));
                int endIdx = curMsg.indexOf(end);
                if (endIdx != -1) {
                    String fullMessage = curMsg.substring(0, endIdx + end.length());
                    curMsg.delete(0, endIdx + end.length());
                    return fullMessage;
                }
            }
        }

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
                int index = readBT.indexOf("sla");
                if (index != -1)
                {
                    String command = readBT.substring(index+3,readBT.length()-1);
                    String commandType = command.substring(0,3);

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(BluetoothService.this);
                    SharedPreferences.Editor edit = prefs.edit();

                    if(commandType.equals("upd")){
                        String commandDatas = command.substring(4,command.length()-1);

                        //Save data in preferences
                        String[] splitString = commandDatas.split("/");
                        edit.putString("temperature",splitString[0] + "°C");
                        edit.putString("humidity", splitString[1] + "%");
                        edit.putString("light", splitString[2] + "%");
                        int time = Integer.parseInt(splitString[3]);
                        if (time > 60)
                            edit.putString("nextWatering", time / 60 + "mn");
                        else
                            edit.putString("nextWatering", time + "s");
                        edit.putString("isLightOn", splitString[4]);
                        edit.putString("lastSync", "Last sync " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));

                        edit.commit();

                        //And notify application to change according to preferences
                        if(MainActivity.active){
                            Intent intent = new Intent(Protocol.BM_BLUETOOTH);
                            intent.putExtra("type", Protocol.ProtoRead.UPDATE);
                            LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
                            readBT = null;
                        }
                    }else if(commandType.equals("not")){
                        String subcommand = command.substring(3,6);
                        if(subcommand.equals("lig")){
                            //Save data in preferences
                            edit.putString("light", readBT.substring(9,readBT.length()-1) + "%");
                            edit.commit();

                            //And notify application to change according to preferences
                            if(MainActivity.active){
                                Intent intent = new Intent(Protocol.BM_BLUETOOTH);
                                intent.putExtra("type", Protocol.ProtoRead.NOTIF_LIGHT);
                                LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
                                readBT = null;
                            }else{
                                Tools.makeNotification(getBaseContext(),
                                        "Highly fluctuating light",
                                        "Change to " + readBT.substring(9,readBT.length()-1) + "%");
                            }
                        }
                    }
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
