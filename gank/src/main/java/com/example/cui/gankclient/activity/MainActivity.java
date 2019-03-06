package com.example.cui.gankclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.cui.gankclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {


    @BindView(R.id.button_main)
    Button buttonMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.button_main)
    public void onViewClicked() {

        Intent intent = new Intent(this, MainTabActivity.class);

        startActivity(intent);
    }
}