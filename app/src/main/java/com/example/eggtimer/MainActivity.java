package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timeTextView;
    SeekBar timeSeekBar;
    Button switchButton;
    Boolean timerIsActive;
    CountDownTimer countDownTimer;

    public void changeTime(int leftTime) {
        int min = leftTime / 60;
        int sec = leftTime % 60;
        String secString = Integer.toString(sec);
        if(sec < 10) {
            secString = "0" + secString;
        }
        timeTextView.setText(min + ":" + secString);
    }

    public void switchClick(View view) {

        if(timerIsActive) {
            timeSeekBar.setEnabled(true);
            timerIsActive = false;
            switchButton.setText("START");
            countDownTimer.cancel();
        } else {
            timeSeekBar.setEnabled(false);
            timerIsActive = true;
            switchButton.setText("STOP");
            countDownTimer = new CountDownTimer(timeSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    timeSeekBar.setProgress((int) l / 1000);
                    changeTime((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    timeSeekBar.setEnabled(true);
                    switchButton.setText("START");
                    timerIsActive = false;
                    timeSeekBar.setProgress(30);
                    timeTextView.setText("0:30");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                }
            }.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeSeekBar = (SeekBar) findViewById(R.id.timeSeekBar);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        switchButton = (Button) findViewById(R.id.switchButton);
        timeSeekBar.setMax(300);
        timerIsActive = false;
        timeSeekBar.setProgress(30);
        timeTextView.setText("0:30");

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int min = i / 60;
                int sec = i % 60;
                String secString = Integer.toString(sec);
                if(sec < 10) {
                    secString = "0" + secString;
                }
                timeTextView.setText(min + ":" + secString);
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