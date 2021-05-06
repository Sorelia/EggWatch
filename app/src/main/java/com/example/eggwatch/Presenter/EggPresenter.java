package com.example.eggwatch.Presenter;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.eggwatch.Model.EggClock;
import com.example.eggwatch.R;
import com.example.eggwatch.View.IEggClockView;

import java.io.InputStream;

import static android.content.ContentValues.TAG;


public class EggPresenter implements  IEggPresenter {

    IEggClockView eggClockView;
    CountDownTimer myTimer;
    public String text;
    boolean isActive = false;
    private final Context context;

    public EggPresenter(IEggClockView eggClockView, Context context){
        this.eggClockView = eggClockView;
        this.context = context;
    }


    ///Checks up on whether isActive variable is true or false,
    ///If false, then create the actual timer
    ///Otherwise if it's created, return null
    @Override
    public CountDownTimer CreateTimer(long time, long delay, String name) {
        EggClock eggClock = new EggClock(time,delay,name);

        if(!isActive){
            myTimer = new CountDownTimer(eggClock.GetTime(), eggClock.GetDelay()){
                @Override
                public void onTick(long millisUntilFinished) {
                    text = "" + millisUntilFinished / 60000 + ":" + (millisUntilFinished % 60000 / 1000);
                    eggClockView.onEggClockResults(text, millisUntilFinished);
                }
                @Override
                public void onFinish() {
                    switch (eggClock.GetName()){
                        case "Soft":
                            text = context.getString(R.string.sunnySide) + " " + context.getString(R.string.done);
                            break;
                        case "Smiling":
                            text = context.getString(R.string.smiling) + " " + context.getString(R.string.done);
                            break;
                        case "Hard":
                            text = context.getString(R.string.hardBoiled) + " " + context.getString(R.string.done);
                    }

                    eggClockView.onEggClockResults(text, 0);
                    /// Creates the ringtone and plays for 10 seconds
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                    //int resID= context.getResources().getIdentifier("rooster", "raw", context.getPackageName()); // Get a resource if R.something.id doesn't work

                    MediaPlayer mp = MediaPlayer.create(context,R.raw.rooster);
                    mp.start();
                    CountDownTimer alarmCDT = new CountDownTimer(12000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            mp.start();
                        }

                        @Override
                        public void onFinish() {
                            mp.stop();
                        }
                    };
                    alarmCDT.start();
                }
            };
            return myTimer;
        }
        else {
            return null;
        }
    }
}
