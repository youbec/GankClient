package com.example.cui.gankclient;

import android.app.Application;

import com.example.cui.gankclient.utils.BuglyUtil;

import org.litepal.LitePal;

/**
 * Created by Cui on 2019/2/16.
 */

public class MyApplication extends Application {
    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        this.application = this;

        LitePal.initialize(this);

        BuglyUtil.init(this);
    }

    public static MyApplication getApplication() {
        return application;
    }
}
