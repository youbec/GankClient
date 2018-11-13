package com.example.cui.gankclient.presenter;

import android.content.Intent;

import com.example.cui.gankclient.view.View;


public interface Presenter {
    void onCreate();

    void onStart();

    void onStop();

    void pause();

    void attachView(View view);


    void attatchIncomingIntent(Intent intent);
}
