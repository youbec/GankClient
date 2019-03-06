package com.example.cui.gankclient.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cui.gankclient.R;
import com.example.cui.gankclient.bean.Collect;
import com.example.cui.gankclient.bean.ResultsBean;
import com.example.cui.gankclient.utils.AndroidUtil;
import com.example.cui.gankclient.utils.ToastUtil;
import com.just.agentweb.AgentWeb;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WebActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private AgentWeb mAgentWeb;
    private ResultsBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LinearLayout linearLayout = findViewById(R.id.linear);
        bean = (ResultsBean) getIntent().getSerializableExtra("bean");


        textTitle.setText(bean.getDesc());


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAgentWeb.back()) {
                    finish();
                }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        List<Collect> all = Collect.findAll(Collect.class);
//
//        for (int i = 0; i < all.size(); i++) {
//            System.out.println(all.get(i));
//        }

        switch (item.getItemId()) {
            case R.id.menu_collect:

                List<Collect> collects = DataSupport.where("url=?", bean.getUrl()).find(Collect.class);

                if (collects.size()==0){
                    Collect collect = new Collect();
                    collect.setPublishedAt(bean.getPublishedAt());
                    collect.setDesc(bean.getDesc());
                    collect.setWho(bean.getWho());
                    collect.setUrl(bean.getUrl());

                    if (bean.getImages()==null||bean.getImages().get(0).equals("")){
                        collect.setImage("");
                    }else {
                        collect.setImage(bean.getImages().get(0));
                    }
                    collect.setType(bean.getType());

                    boolean save = collect.save();

                    if (save){
                        ToastUtil.showToast(this, "收藏成功");

                    }else {
                        ToastUtil.showToast(this, "失败");
                    }


                }else {
                    ToastUtil.showToast(this, "已在收藏列表中");
                }

                break;
            case R.id.menu_share:
                AndroidUtil.share(this, bean.getUrl());
                break;
            case R.id.menu_copy_link:
                if (AndroidUtil.copyText(this, bean.getUrl())) {
                    ToastUtil.showToast(this, "链接复制成功");
                }
                break;
            case R.id.menu_open_with:
                AndroidUtil.openWithBrowser(this, bean.getUrl());
                break;
        }
        return super.onOptionsItemSelected(item);
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

}
