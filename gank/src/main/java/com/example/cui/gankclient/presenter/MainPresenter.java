package com.example.cui.gankclient.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.cui.gankclient.bean.MainBean;
import com.example.cui.gankclient.manager.DataManager;
import com.example.cui.gankclient.view.MainBeanView;
import com.example.cui.gankclient.view.View;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Cui on 2018/10/6 0006.
 */

public class MainPresenter implements Presenter{
    private DataManager dataManager;
    //private CompositeDisposable compositeDisposable;
    private Context mContext;
    private MainBean mMainBean;
    private MainBeanView mainBeanView;


    public MainPresenter(Context context) {
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

    public void getMainInfo(String type , final int num, int page){
        dataManager.getAndroidInfo(type,num,page)
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
                mainBeanView.onError("网络异常，请连接移动网络或WIFI");
            }

            @Override
            public void onComplete() {
                mainBeanView.onSuccess(mMainBean);
            }
        });

    }

}
