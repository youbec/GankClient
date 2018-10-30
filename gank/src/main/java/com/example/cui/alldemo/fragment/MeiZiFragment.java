package com.example.cui.alldemo.fragment;

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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cui.alldemo.R;
import com.example.cui.alldemo.activity.BigPhotoActivity;
import com.example.cui.alldemo.adapter.MeiZiAdapter;
import com.example.cui.alldemo.bean.MeiZi;
import com.example.cui.alldemo.presenter.MeiZiPresenter;
import com.example.cui.alldemo.view.MeiZiView;
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

        onRefresh();
        return view;
    }

    private MeiZiView meiZiView = new MeiZiView() {
        @Override
        public void onSuccess(final MeiZi meiZi) {
            avi.hide();
            results = meiZi.getResults();
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
            meiZiAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            meiZiAdapter.isFirstOnly(false);

            meiZiAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                    Intent intent = new Intent(getActivity(), BigPhotoActivity.class);
                    MeiZi.ResultsBean bean = (MeiZi.ResultsBean) adapter.getData().get(position);
                    intent.putExtra(BigPhotoActivity.URL, bean.getUrl());
                    startActivity(intent);
                }
            });
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
            Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onRefresh() {
        page = 1;
        isFirstPage = true;
        meiZiPresenter.getMeiZiInfo(10, page);

    }

}
