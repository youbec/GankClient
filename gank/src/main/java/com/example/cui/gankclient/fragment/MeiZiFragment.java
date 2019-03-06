package com.example.cui.gankclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cui.gankclient.R;
import com.example.cui.gankclient.activity.BigPhotoActivity;
import com.example.cui.gankclient.adapter.MeiZiAdapter;
import com.example.cui.gankclient.bean.MeiZi;
import com.example.cui.gankclient.presenter.MeiZiPresenter;
import com.example.cui.gankclient.utils.ACache;
import com.example.cui.gankclient.view.MeiZiView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chad.library.adapter.base.BaseQuickAdapter.ALPHAIN;

/**
 * Created by Cui on 2018/10/15.
 */

public class MeiZiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.android_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.android_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;


    private MeiZiPresenter meiZiPresenter = new MeiZiPresenter(getActivity());
    private MeiZiAdapter meiZiAdapter;
    private List<MeiZi.ResultsBean> results;

    //加载更多
    private int page = 1;
    private boolean isFirstPage = true;
    private BaseQuickAdapter.OnItemChildClickListener onClickListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meizi, null);
        ButterKnife.bind(this, view);
        avi.show();

        meiZiPresenter.onCreate();
        meiZiPresenter.attachView(meiZiView);

        swipeRefreshLayout.setOnRefreshListener(this);

      //  LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
      //  layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        StaggeredGridLayoutManager recyclerViewLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(meiZiAdapter = new MeiZiAdapter(new ArrayList<MeiZi.ResultsBean>()));
        onClickListener = new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(getActivity(), BigPhotoActivity.class);
                MeiZi.ResultsBean bean = (MeiZi.ResultsBean) adapter.getData().get(position);
                intent.putExtra(BigPhotoActivity.URL, bean.getUrl());
                startActivity(intent);
            }
        };
        onRefresh();
        return view;
    }

    private MeiZiView meiZiView = new MeiZiView() {
        @Override
        public void onSuccess(final MeiZi meiZi) {
            avi.hide();
            results = meiZi.getResults();

            JSONArray jsonArray=new JSONArray();
            String meizi = jsonArray.toJSONString(results);
            ACache.get(getActivity()).put("meizi",meizi,60*60);

            swipeRefreshLayout.setRefreshing(false);
            meiZiAdapter.loadMoreComplete();
            if (isFirstPage) {
                meiZiAdapter.setNewData(meiZi.getResults());
            } else {
                meiZiAdapter.addData(meiZi.getResults());
            }

            //添加打开动画
            meiZiAdapter.openLoadAnimation(ALPHAIN);
            //列表动画
//            meiZiAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
//            meiZiAdapter.isFirstOnly(false);

            meiZiAdapter.setOnItemChildClickListener(onClickListener);
            meiZiAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    isFirstPage = false;
                    page++;
                    meiZiPresenter.getMeiZiInfo(10, page);
                }
            }, recyclerView);
        }

        @Override
        public void onError(String result) {
            ToastUtils.showShort(result);
            String meizi = ACache.get(getActivity()).getAsString("meizi");
            List<MeiZi.ResultsBean> meiZiList = JSONObject.parseArray(meizi, MeiZi.ResultsBean.class);
            if (meizi==null){
                swipeRefreshLayout.setRefreshing(false);
            }else {
                swipeRefreshLayout.setRefreshing(false);
                avi.hide();
                meiZiAdapter.setNewData(meiZiList);
                meiZiAdapter.setOnItemChildClickListener(onClickListener);

            }
        }
    };

    @Override
    public void onRefresh() {
        page = 1;
        isFirstPage = true;
        meiZiPresenter.getMeiZiInfo(10, page);

    }

}
