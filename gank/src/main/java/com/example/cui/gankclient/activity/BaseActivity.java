package com.example.cui.gankclient.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

/**
 * Created by Cui on 2019/2/28.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }



}
