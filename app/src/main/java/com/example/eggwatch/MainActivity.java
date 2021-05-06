package com.example.eggwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.eggwatch.Presenter.EggPresenter;
import com.example.eggwatch.Presenter.IEggPresenter;
import com.example.eggwatch.View.IEggClockView;

public class MainActivity extends AppCompatActivity  {

    private int delay;
    private long mTimeLeftInMillis;
    private long mMin;
    private long mSec;
    private boolean isActive = false;
    CountDownTimer myTimer;
    IEggPresenter iEggPresenter;
    Button start;
    TextView timer;
    ImageButton soft;
    ImageButton medium;
    ImageButton hard;
    Button stop;
    TextView eggChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* Initiating the needed Variables*/
        start = findViewById(R.id.startButton);
        timer = findViewById(R.id.intervalTime);
        soft = findViewById(R.id.sunnySideButton);
        medium = findViewById(R.id.smilingButton);
        hard = findViewById(R.id.hardBoiledButton);
        stop = findViewById(R.id.stopButton);
        eggChosen = findViewById(R.id.titleText);
        delay = 1000;
        start.setEnabled(false);
        stop.setEnabled(false);


        ///Updates View
        IEggClockView clockView = new IEggClockView() {
            @Override
            public void onEggClockResults(String text, long remainingTime) {
                TextView timer = findViewById(R.id.intervalTime);
                timer.setText(text);
                mTimeLeftInMillis = remainingTime;
                if (mTimeLeftInMillis == 0){
                    start.setText(R.string.start);
                    start.setEnabled(false);
                    stop.setEnabled(false);
                    soft.setEnabled(true);
                    medium.setEnabled(true);
                    hard.setEnabled(true);
                    eggChosen.setText(R.string.title);
                    isActive = false;
                }
            }
        };
        iEggPresenter = new EggPresenter(clockView,this);
    }

    // Checks which button is being clicked
    // And handles the functionality afterwards
    public void OnEggClick(View view) {

        /// Switch case handling the actual cases
        /// Creates the actual timer, Set's the text, and size
        switch (view.getId()){
            case R.id.sunnySideButton:
                start.setEnabled(true);
                mTimeLeftInMillis = 300000;
                mMin = mTimeLeftInMillis / 60000;
                mSec = (mTimeLeftInMillis % 60000 / 1000);
                timer.setText(mMin + ":" + mSec);
                eggChosen.setText(R.string.sunnySide);
                myTimer = iEggPresenter.CreateTimer(mTimeLeftInMillis, delay, "Soft");
            break;
            case R.id.smilingButton:
                start.setEnabled(true);
                mTimeLeftInMillis = 420000;
                mMin = mTimeLeftInMillis / 60000;
                mSec = (mTimeLeftInMillis % 60000 / 1000);
                timer.setText(mMin + ":" + mSec);
                eggChosen.setText(R.string.smiling);
                myTimer = iEggPresenter.CreateTimer(mTimeLeftInMillis, delay, "Smiling");
                break;
            case R.id.hardBoiledButton:
                start.setEnabled(true);
                mTimeLeftInMillis = 600000;
                mMin = mTimeLeftInMillis / 60000;
                mSec = (mTimeLeftInMillis % 60000 / 1000);
                timer.setText(mMin + ":" + mSec);
                eggChosen.setText(R.string.hardBoiled);
                myTimer = iEggPresenter.CreateTimer(mTimeLeftInMillis, delay, "Hard");
                break;
            default:
                throw new RuntimeException("Unknown Button ID");
        }
    }

    /// Starts the timer
    /// Checks if it's active
    ///
    public void StartTimer(View view){
        if(!isActive){
            myTimer.start();
            isActive = true;
            soft.setEnabled(false);
            medium.setEnabled(false);
            hard.setEnabled(false);
            start.setEnabled(true);
            start.setText(R.string.pause);
            stop.setEnabled(false);
        } else
        {
            myTimer.cancel();
            start.setText(R.string.resume);
            start.setEnabled(true);
            stop.setEnabled(true);
            isActive = false;
        }
    }
 /// Stops the timer, Disabling resume
    public void StopTimer(View view){
        start.setEnabled(false);
        start.setText(R.string.start);
        soft.setEnabled(true);
        medium.setEnabled(true);
        hard.setEnabled(true);
        stop.setEnabled(false);
        timer.setText(R.string.timerStopped);
        timer.setTextSize(65);
    }

}