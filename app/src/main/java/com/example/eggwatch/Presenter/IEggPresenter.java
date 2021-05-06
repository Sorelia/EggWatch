package com.example.eggwatch.Presenter;

import android.os.CountDownTimer;


/// Interface with the function needed on presenter
public interface IEggPresenter {
    CountDownTimer CreateTimer(long time, long delay, String name);
}
