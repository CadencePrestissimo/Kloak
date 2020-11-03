package com.example.kloak;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import static java.lang.Integer.valueOf;

public class Timer extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllerButton;
    Button pauseButton;
    ImageView imageView4;
    Boolean counterIsActive = false;
    int time;
    Boolean pau=false;
    CountDownTimer countDownTimer;

    public void pauseTimer()
    {
        updateTimer(time);
        countDownTimer.cancel();
        controllerButton.setText("Go!");
        timerSeekBar.setEnabled(true);
        timerSeekBar.setProgress(time);
        counterIsActive = false;
    }

    public void resetTimer()
    {

        timerTextView.setText("0:00");
        timerSeekBar.setProgress(0);
        countDownTimer.cancel();
        controllerButton.setText("Go!");
        timerSeekBar.setEnabled(true);
        counterIsActive = false;

    }

    public void updateTimer(int secondsLeft) {

        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {

            secondString = "0" + secondString;

        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);

    }

    public void setTime(int t)
    {
        time=t;
    }



    public void controlTimer(View view) {
        view.setAlpha(0.3f);
        view.animate().alpha(1f);

        if (counterIsActive == false ) {

            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("Pause");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                    setTime((int) millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    timerTextView.setAlpha(0.6f);
                    timerTextView.animate().alpha(1f).setDuration(500);
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.b1);
                    mplayer.start();
                    resetTimer();

                }

            }.start();

        }
        else {
            pauseTimer();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        imageView4=findViewById(R.id.imageView4);
        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        controllerButton = (Button)findViewById(R.id.controllerButton);
        pauseButton=(Button)findViewById(R.id.pauseButton);

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(0);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

}