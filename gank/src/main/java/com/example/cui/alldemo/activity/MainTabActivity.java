package com.example.cui.alldemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.cui.alldemo.R;
import com.example.cui.alldemo.bean.MainBean;
import com.example.cui.alldemo.fragment.AndroidFragment;
import com.example.cui.alldemo.fragment.AppFragment;
import com.example.cui.alldemo.fragment.Front_endFragment;
import com.example.cui.alldemo.fragment.IsoFragment;
import com.example.cui.alldemo.fragment.MeiZiFragment;
import com.example.cui.alldemo.fragment.RecommendFragment;
import com.example.cui.alldemo.fragment.ResourceFragment;
import com.example.cui.alldemo.fragment.VideoFragment;
import com.example.cui.alldemo.utils.CommonViewPagerAdapter;
import com.example.cui.alldemo.utils.GlobalConfig;
import com.kekstudio.dachshundtablayout.DachshundTabLayout;
import com.kekstudio.dachshundtablayout.indicators.LineFadeIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainTabActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.drawlayout)
    DrawerLayout drawLayout;
    @BindView(R.id.tab)
    DachshundTabLayout mTabLayout;
    @BindView(R.id.rx_url)
    TextView rxUrl;
    @BindView(R.id.ret_url)
    TextView retUrl;
    @BindView(R.id.gli_url)
    TextView gliUrl;
    @BindView(R.id.but_url)
    TextView butUrl;
    @BindView(R.id.bas_url)
    TextView basUrl;
    @BindView(R.id.tab_url)
    TextView tabUrl;
    @BindView(R.id.web_url)
    TextView webUrl;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        ButterKnife.bind(this);

        toolbar.setTitle("Gank");
        toolbar.setNavigationIcon(R.drawable.ic_menu);


        setSupportActionBar(toolbar);
        //fragment list
        fragmentList = new ArrayList<>();

        String[] titles = {
                GlobalConfig.CATEGORY_NAME_ANDROID,
                GlobalConfig.CATEGORY_NAME_IOS,
                GlobalConfig.CATEGORY_NAME_video,
                GlobalConfig.CATEGORY_NAME_WOMAN,
                GlobalConfig.CATEGORY_NAME_RESOURCE,
                GlobalConfig.CATEGORY_NAME_FRONT_END,
                GlobalConfig.CATEGORY_NAME_RECOMMEND,
                GlobalConfig.CATEGORY_NAME_app
        };

        CommonViewPagerAdapter infoPagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(), titles);

        infoPagerAdapter.addFragment(new AndroidFragment());
        infoPagerAdapter.addFragment(new IsoFragment());
        infoPagerAdapter.addFragment(new VideoFragment());
        infoPagerAdapter.addFragment(new MeiZiFragment());
        infoPagerAdapter.addFragment(new ResourceFragment());
        infoPagerAdapter.addFragment(new Front_endFragment());
        infoPagerAdapter.addFragment(new RecommendFragment());
        infoPagerAdapter.addFragment(new AppFragment());

        viewPager.setAdapter(infoPagerAdapter);
        mTabLayout.setupWithViewPager(viewPager);
        // mTabLayout.setAnimatedIndicator(new LineFadeIndicator(mTabLayout).setSelectedTabIndicatorHeight(2));
        LineFadeIndicator lineFadeIndicator = new LineFadeIndicator(mTabLayout);
        mTabLayout.setSelectedTabIndicatorColor(0);
        mTabLayout.setSelectedTabIndicatorHeight(5);
        mTabLayout.setAnimatedIndicator(lineFadeIndicator);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                drawLayout.openDrawer(findViewById(R.id.include_nav_header_main));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    long firstBackTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstBackTime > 2000) {
                Snackbar.make(drawLayout, "再按一次退出应用", Snackbar.LENGTH_SHORT).show();
                firstBackTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @OnClick({R.id.rx_url, R.id.ret_url, R.id.gli_url, R.id.but_url, R.id.bas_url, R.id.tab_url, R.id.web_url})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rx_url:
               StartIntent(rxUrl.getText().toString());
                break;
            case R.id.ret_url:
                StartIntent(retUrl.getText().toString());
                break;
            case R.id.gli_url:
                StartIntent(gliUrl.getText().toString());
                break;
            case R.id.but_url:
                StartIntent(butUrl.getText().toString());
                break;
            case R.id.bas_url:
                StartIntent(basUrl.getText().toString());
                break;
            case R.id.tab_url:
                StartIntent(tabUrl.getText().toString());
                break;
            case R.id.web_url:
                StartIntent(webUrl.getText().toString());
                break;
        }
    }


    public void StartIntent(String text){
        MainBean.ResultsBean bean = new MainBean.ResultsBean();
        bean.setUrl(text);
        bean.setDesc(text);
        Intent intent = new Intent(this,WebActivity.class);
        intent.putExtra("bean",bean);
        startActivity(intent);
    }
}
