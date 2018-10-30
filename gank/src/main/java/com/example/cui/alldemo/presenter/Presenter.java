package com.example.cui.alldemo.presenter;

import android.content.Intent;

import com.example.cui.alldemo.view.View;


/**
 * Created by lynn on 2018/1/18.
 */

public interface Presenter {
    void onCreate();

    void onStart();

    void onStop();

    void pause();

    void attachView(View view);


    void attatchIncomingIntent(Intent intent);
}
