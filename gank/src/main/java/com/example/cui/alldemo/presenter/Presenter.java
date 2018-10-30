package com.example.cui.alldemo.presenter;

import android.content.Intent;

import com.example.cui.alldemo.view.View;


public interface Presenter {
    void onCreate();

    void onStart();

    void onStop();

    void pause();

    void attachView(View view);


    void attatchIncomingIntent(Intent intent);
}
