package com.example.kloak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.animation.LayoutTransition;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class StopWatch extends AppCompatActivity {

    private Button startButton;
    private Button lapButton;
    private TextView textView;
    private Timer timer;
    private NotificationCompat.Builder builder;
    private NotificationManager manager;
    private LayoutTransition transition;
    private LinearLayout linearLayout;
    private int currentTime = 0;
    private int lapTime = 0;
    private int lapCounter = 0;
    private int mId = 1;
    private boolean lapViewExists;
    private boolean isButtonStartPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        startButton = (Button) findViewById(R.id.btn_start);
        lapButton = (Button) findViewById(R.id.btn_lap);
        textView = (TextView) findViewById(R.id.stopwatch_view);
        textView.setTextSize(55);
    }

    public void onSWatchStart(View view) {
        if (isButtonStartPressed) {
            onSWatchStop();
        } else {
            isButtonStartPressed = true;

            startButton.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(100,0,0)));
            startButton.setText("Stop");

            lapButton.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(25,0,0)));
            lapButton.setText("Lap");
            lapButton.setEnabled(true);

            setUpNotification();

            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            currentTime += 1;
                            lapTime += 1;

                            manager = (NotificationManager)
                                    getSystemService(Context.NOTIFICATION_SERVICE);

                            // update notification text
                            builder.setContentText(TimeFormatUtil.toDisplayString(currentTime));
                            manager.notify(mId, builder.build());

                            // update ui
                            textView.setText(TimeFormatUtil.toDisplayString(currentTime));
                        }
                    });
                }
            }, 0, 10);
        }
    }

    public void onSWatchStop() {
        startButton.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(50 ,153 ,153)));
        startButton.setText("Start");
        lapButton.setEnabled(true);
        lapButton.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(50,153,153)));
        lapButton.setText("Reset");

        isButtonStartPressed = false;
        timer.cancel();
        manager.cancel(mId);
    }

    public void onSWatchReset() {
        timer.cancel();
        currentTime = 0;
        lapTime = 0;
        lapCounter = 0;
        textView.setText(TimeFormatUtil.toDisplayString(currentTime));
        lapButton.setEnabled(false);
        lapButton.setText("Lap");
        lapButton.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(50,153,153)));

        if (lapViewExists) {
            linearLayout.setLayoutTransition(null);
            linearLayout.removeAllViews();
            lapViewExists = false;
        }
    }

    public void onSWatchLap(View view) {
        if (!isButtonStartPressed) {
            onSWatchReset();
        } else {
            lapViewExists = true;
            lapCounter++;

            transition = new LayoutTransition();
            transition.setAnimator(LayoutTransition.CHANGE_APPEARING, null);
            transition.setStartDelay(LayoutTransition.APPEARING, 0);

            linearLayout = (LinearLayout) findViewById(R.id.layout);
            linearLayout.setLayoutTransition(transition);

            TextView lapDisplay = new TextView(this);
            ImageView imageView = new ImageView(this);
            imageView.setFocusableInTouchMode(true);

            lapDisplay.setGravity(Gravity.CENTER);
            lapDisplay.setTextColor(Color.WHITE);
            lapDisplay.setTextSize(20);

            linearLayout.addView(lapDisplay);
            linearLayout.addView(imageView);

            imageView.requestFocus();

            lapDisplay.setText(String.format("Lap %d: %s", lapCounter, TimeFormatUtil.toDisplayString(lapTime)));
            imageView.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
            lapTime = 0;
        }
    }

    public void setUpNotification() {
        builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_baseline_add_alarm_24)
                        .setContentTitle("Stopwatch running")
                        .setContentText("00:00:00")
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setOngoing(true);


        Intent resultIntent = new Intent(this, StopWatch.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

    }



}