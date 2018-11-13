package com.example.cui.gankclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cui.gankclient.R;
import com.example.cui.gankclient.adapter.MainAdapter;
import com.example.cui.gankclient.bean.MainBean;
import com.example.cui.gankclient.presenter.SearchPresenter;
import com.example.cui.gankclient.view.MainBeanView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chad.library.adapter.base.BaseQuickAdapter.ALPHAIN;

public class SearchActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.searchet)
    EditText searchet;
    @BindView(R.id.android_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.android_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.backtitle)
    ImageView backtitle;
    @BindView(R.id.searchiv)
    ImageView searchiv;

    private SearchPresenter searchPresenter = new SearchPresenter(this);
    private MainAdapter mainAdapter;
    private List<MainBean.ResultsBean> results;

    //加载更多
    private int page = 1;
    private boolean isFirstPage = true;
    private String content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach);
        ButterKnife.bind(this);
        searchPresenter.onCreate();
        searchPresenter.attachView(mainBeanView);
        swipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainAdapter = new MainAdapter(new ArrayList<MainBean.ResultsBean>()));

        backtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = searchet.getText().toString();
                Toast.makeText(SearchActivity.this, content,Toast.LENGTH_SHORT).show();
                onRefresh();
            }
        });

        //设置输入就查询
        searchet.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                }
                content = searchet.getText().toString();
                if (content.isEmpty()) {
                }else {
                    searchPresenter.getSearchInfo(content, 5, page);
                }
                return false;
            }
        });
    }


    private MainBeanView mainBeanView = new MainBeanView() {
        @Override
        public void onSuccess(final MainBean mainBean) {
            results = mainBean.getResults();

            swipeRefreshLayout.setRefreshing(false);
            mainAdapter.loadMoreComplete();
            if (isFirstPage) {
                mainAdapter.setNewData(mainBean.getResults());
            } else {
                mainAdapter.addData(mainBean.getResults());
            }

            //添加打开动画
            mainAdapter.openLoadAnimation(ALPHAIN);
            //列表动画
            mainAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            mainAdapter.isFirstOnly(false);

            mainAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                    Intent intent = new Intent(SearchActivity.this, WebActivity.class);
                    MainBean.ResultsBean bean = (MainBean.ResultsBean) adapter.getData().get(position);
                    intent.putExtra("bean", bean);
                    startActivity(intent);
                }
            });
            mainAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    isFirstPage = false;
                    page++;
                    searchPresenter.getSearchInfo(content, 5, page);
                }
            }, recyclerView);
        }

        @Override
        public void onError(String result) {
            Toast.makeText(SearchActivity.this, result, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onRefresh() {
        page = 1;
        isFirstPage = true;
        searchPresenter.getSearchInfo(content, 5, page);
    }
}
