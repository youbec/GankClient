package com.example.cui.gankclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cui.gankclient.activity.WebActivity;
import com.example.cui.gankclient.adapter.MainAdapter;
import com.example.cui.gankclient.bean.MainBean;
import com.example.cui.gankclient.presenter.MainPresenter;
import com.example.cui.gankclient.view.MainBeanView;
import com.example.cui.gankclient.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chad.library.adapter.base.BaseQuickAdapter.ALPHAIN;

/**
 * Created by Cui on 2018/10/16.
 */

public class VideoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.android_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.android_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;


    private MainPresenter mainPresenter = new MainPresenter(getActivity());
    private MainAdapter mainAdapter;
    private List<MainBean.ResultsBean> results;

    //加载更多
    private int page = 1;
    private boolean isFirstPage = true;
    private String s;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_android, null);
        ButterKnife.bind(this, view);
        avi.show();

        mainPresenter.onCreate();
        mainPresenter.attachView(mainBeanView);

        swipeRefreshLayout.setOnRefreshListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainAdapter = new MainAdapter(new ArrayList<MainBean.ResultsBean>()));

        onRefresh();
        return view;
    }

    private MainBeanView mainBeanView = new MainBeanView() {
        @Override
        public void onSuccess(final MainBean mainBean) {
            avi.hide();
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

                    Intent intent = new Intent(getActivity(), WebActivity.class);
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
                    mainPresenter.getMainInfo("休息视频", 10, page);
                }
            }, recyclerView);
        }

        @Override
        public void onError(String result) {
            Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onRefresh() {
        page = 1;
        isFirstPage = true;
        mainPresenter.getMainInfo("休息视频", 10, page);

    }
}
