package com.example.cui.gankclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cui.gankclient.R;
import com.example.cui.gankclient.adapter.MainAdapter;
import com.example.cui.gankclient.bean.MainBean;
import com.example.cui.gankclient.bean.ResultsBean;
import com.example.cui.gankclient.presenter.MainPresenter;
import com.example.cui.gankclient.utils.ACache;
import com.example.cui.gankclient.view.MainBeanView;
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
    private List<ResultsBean> results;

    //加载更多
    private int page = 1;
    private boolean isFirstPage = true;
    private BaseQuickAdapter.OnItemClickListener onClickListener;
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
        recyclerView.setAdapter(mainAdapter = new MainAdapter(new ArrayList<ResultsBean>()));

        onRefresh();
        return view;
    }

    private MainBeanView mainBeanView = new MainBeanView() {
        @Override
        public void onSuccess(final MainBean mainBean) {
            avi.hide();
            results = mainBean.getResults();

            JSONArray jsonArray=new JSONArray();
            String video = jsonArray.toJSONString(results);
            ACache.get(getActivity()).put("video",video,60*60);

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
//            mainAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
//            mainAdapter.isFirstOnly(false);

            mainAdapter.setOnItemClickListener(onClickListener);
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
            ToastUtils.showShort(result);
            String video = ACache.get(getActivity()).getAsString("video");
            List<ResultsBean> resultsBeen = JSONObject.parseArray(video, ResultsBean.class);
            if (video==null){
                swipeRefreshLayout.setRefreshing(false);
            }else {
                swipeRefreshLayout.setRefreshing(false);
                avi.hide();
                mainAdapter.setNewData(resultsBeen);
                mainAdapter.setOnItemClickListener(onClickListener);
            }
        }
    };

    @Override
    public void onRefresh() {
        page = 1;
        isFirstPage = true;
        mainPresenter.getMainInfo("休息视频", 10, page);

    }
}
