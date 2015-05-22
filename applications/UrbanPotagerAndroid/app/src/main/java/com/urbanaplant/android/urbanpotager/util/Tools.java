package com.urbanaplant.android.urbanpotager.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.urbanaplant.android.urbanpotager.MainActivity;
import com.urbanaplant.android.urbanpotager.R;

/**
 * Created by Tatiana Grange on 18/02/2015.
 */
public class Tools {

    public static boolean isDev = true;

    /**
     * This is the tag for log in the application
     */
    public static final String TAG = "Urbanpotager";

    /**
     * This method Log, always on "i", always with TAG
     * @param s
     */
    public static void log(String s){
        Log.i(TAG, s);
    }

    /**
     * Check if a CharSequence is or not a valide email adress
     * @param email
     * @return boolean
     */
    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Check if an EditText is Empty or not
     * @param et
     * @return
     */
    public static boolean isEditTextEmpty(EditText et){
        return et.getText().toString().matches("");
    }

    /**
     * Show a View V with an alpha fadeIn Animation, in time milliseconds with Animation Listener pass in argument
     * @param v
     * @param time
     * @param listener
     */
    public static void show(final View v, int time, final AnimatorListenerAdapter listener){
        v.setVisibility(View.VISIBLE);
        v.animate()
                .alpha(1f)
                .setDuration(time)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.VISIBLE);
                        v.setEnabled(true);
                        if(listener != null)
                            listener.onAnimationEnd(animation);
                    }
                });
    }

    /**
     * Show a View V with an alpha fadeIn Animation, in time milliseconds
     * @param v
     * @param time
     */
    public static void show(View v, int time){
        Tools.show(v,time,null);
    }


    /**
     * Hide a View V with an alpha fadeOut Animation, in time milliseconds with Animation Listener pass in argument
     * @param v
     * @param time
     * @param listener
     */
    public static void hide(final View v, int time, final AnimatorListenerAdapter listener){
        v.animate()
                .alpha(0f)
                .setDuration(time)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.GONE);
                        v.setEnabled(false);
                        if(listener != null)
                            listener.onAnimationEnd(animation);
                    }

                });
    }

    /**
     * Hide a View V with an alpha fadeOut Animation, in time milliseconds
     * @param v
     * @param time
     */
    public static void hide(final View v, int time){
        Tools.hide(v,time,null);
    }

    /**
     *
     * @param context - The Context where the Toast have to show
     * @param text - The String show in the Toast
     */
    public static void toast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     *
     * @param context - The Context where the Notification come from
     * @param title - The notification title
     * @param content - The notification content
     */
    public static void makeNotification(Context context, String title, String content){
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_notif)
                        .setContentTitle(title)
                        .setContentText(content);
        int NOTIFICATION_ID = 12345;

        Intent targetIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());
    }
}
