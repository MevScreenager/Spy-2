package com.example.spy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.media.RingtoneManager;
import android.os.Build;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Time extends Activity {
    private int mCurrentPeriod = 0;
    private boolean mIsRunning = false;
    private Timer myTimer;
    private int time;
    private TextView timer;
    private Context context;

    public Time(TextView timer, int time, Context context) {
        this.timer = timer;
        this.time = time;
        this.context = context;
    }

    public void onStartButtonClick() {
        mCurrentPeriod = time * 60;
        if (mCurrentPeriod == 0) return;
        mIsRunning = true;
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }
        }, 0, 1000);
    }

    private void TimerMethod() {
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        public void run() {
            mCurrentPeriod --;
            timer.setText(intToTime(mCurrentPeriod));
            if (mCurrentPeriod == 0) {
                stop();
                mIsRunning = false;
//                setNotification();
            }
        }
    };
    public void setNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this)
                .setContentTitle("Время пришло!")
                .setContentText("Таймер истек")
//                .setSmallIcon(R.drawable.indicator_input_error)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String intToTime(int i) {
        return (new SimpleDateFormat("mm:ss")).format(new Date(i * 1000));
    }

    public boolean getMIsRunning(){
        return mIsRunning;
    }

    public void stop(){
        myTimer.cancel();
    }

}
