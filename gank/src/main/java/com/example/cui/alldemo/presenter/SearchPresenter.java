package com.example.cui.alldemo.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.cui.alldemo.bean.MainBean;
import com.example.cui.alldemo.manager.DataManager;
import com.example.cui.alldemo.view.MainBeanView;
import com.example.cui.alldemo.view.View;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Cui on 2018/10/6 0006.
 */

public class SearchPresenter implements Presenter{
    private DataManager dataManager;
    //private CompositeDisposable compositeDisposable;
    private Context mContext;
    private MainBean mMainBean;
    private MainBeanView mainBeanView;


    public SearchPresenter(Context context) {
        this.mContext = context;
    }


    @Override
    public void onCreate() {
        dataManager = new DataManager(mContext);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void attachView(View view) {
        mainBeanView = (MainBeanView) view;
    }


    @Override
    public void attatchIncomingIntent(Intent intent) {

    }

    public void getSearchInfo(String type , final int num, int page){
        dataManager.getSearchInfo(type,num,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MainBean>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull MainBean mainBean) {
                mMainBean = mainBean;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mainBeanView.onError("请求错误");
            }

            @Override
            public void onComplete() {
                mainBeanView.onSuccess(mMainBean);
            }
        });

    }

}
