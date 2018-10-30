package com.example.cui.alldemo.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.cui.alldemo.bean.MeiZi;
import com.example.cui.alldemo.manager.DataManager;
import com.example.cui.alldemo.view.MeiZiView;
import com.example.cui.alldemo.view.View;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Cui on 2018/10/7 0007.
 */

public class MeiZiPresenter implements Presenter {

    private DataManager dataManager;
    //private CompositeDisposable compositeDisposable;
    private Context mContext;

    private MeiZi mMeiZi;
    private MeiZiView mMeiZiView;

    public MeiZiPresenter(Context context) {
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
        mMeiZiView = (MeiZiView) view;
    }

    @Override
    public void attatchIncomingIntent(Intent intent) {

    }


    public void getMeiZiInfo(int num,int page){
        dataManager.getMeiZiInfo(num,page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MeiZi>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull MeiZi meiZi) {

                mMeiZi = meiZi;

            }

            @Override
            public void onError(@NonNull Throwable e) {
                mMeiZiView.onError(e.toString());
            }

            @Override
            public void onComplete() {
                mMeiZiView.onSuccess(mMeiZi);
            }
        });
    }

    public MeiZi getMeiZi(int num,int page){
        dataManager.getMeiZiInfo(num,page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MeiZi>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull MeiZi meiZi) {
                mMeiZi = meiZi;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mMeiZiView.onError(e.toString());
            }

            @Override
            public void onComplete() {
                mMeiZiView.onSuccess(mMeiZi);
            }
        });
        return mMeiZi;
    }


}
