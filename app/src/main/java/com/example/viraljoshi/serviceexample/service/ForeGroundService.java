package com.example.viraljoshi.serviceexample.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.viraljoshi.serviceexample.MainActivity;
import com.example.viraljoshi.serviceexample.R;
import com.example.viraljoshi.serviceexample.utils.IConstants;


public class ForeGroundService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("ForeGroundService", "onCreate");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("ForeGroundService", "onStartCommand Actions =" + intent.getAction());

        if (intent.getAction().equals(IConstants.Actions.STARTFOREGROUND_ACTION)) {
            Log.i("Intent", "Received Start Foreground Intent ");
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setAction(IConstants.Actions.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);

            Intent previousIntent = new Intent(this, ForeGroundService.class);
            previousIntent.setAction(IConstants.Actions.PREV_ACTION);
            PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                    previousIntent, 0);

            Intent playIntent = new Intent(this, ForeGroundService.class);
            playIntent.setAction(IConstants.Actions.PLAY_ACTION);
            PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                    playIntent, 0);

            Intent nextIntent = new Intent(this, ForeGroundService.class);
            nextIntent.setAction(IConstants.Actions.NEXT_ACTION);
            PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                    nextIntent, 0);

            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.iconfinder);

            String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";
            String channelName = "My Background Service";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Truiton Music Player")
                    .setTicker("Truiton Music Player")
                    .setContentText("My Music")
                    .setSmallIcon(android.R.drawable.btn_minus)
                    .setLargeIcon(
                            Bitmap.createScaledBitmap(icon, 128, 128, false))
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .addAction(android.R.drawable.ic_media_previous,
                            "Previous", ppreviousIntent)
                    .addAction(android.R.drawable.ic_media_play, "Play",
                            pplayIntent)
                    .addAction(android.R.drawable.ic_media_next, "Next",
                            pnextIntent).
                            setChannelId(NOTIFICATION_CHANNEL_ID).build();
            startForeground(IConstants.FOREGROUND_SERVICE,
                    notification);
        } else if (intent.getAction().equals(IConstants.Actions.PREV_ACTION)) {
            Log.i("LOG_TAG-1", "Clicked Previous");
        } else if (intent.getAction().equals(IConstants.Actions.PLAY_ACTION)) {
            Log.i("LOG_TAG-2", "Clicked Play");
        } else if (intent.getAction().equals(IConstants.Actions.NEXT_ACTION)) {
            Log.i("LOG_TAG-3", "Clicked Next");
        } else if (intent.getAction().equals(
                IConstants.Actions.STOPFOREGROUND_ACTION)) {
            Log.i("LOG_TAG-4", "Received Stop Foreground Intent");
            stopForeground(true);
            stopSelf();

        }
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("ForeGroundService", "onDestroy");
    }
}
