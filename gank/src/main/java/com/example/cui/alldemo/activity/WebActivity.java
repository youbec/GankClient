package com.example.cui.alldemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cui.alldemo.R;
import com.example.cui.alldemo.bean.MainBean;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WebActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.text_title)
    TextView textTitle;
    private AgentWeb mAgentWeb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        LinearLayout linearLayout = findViewById(R.id.linear);
        MainBean.ResultsBean bean = (MainBean.ResultsBean) getIntent().getSerializableExtra("bean");
        textTitle.setText(bean.getDesc());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //传入Activity
        //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
        // 使用默认进度条
        //
        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(linearLayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条
                .createAgentWeb()//
                .ready()
                .go(bean.getUrl());

    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
    }
}
