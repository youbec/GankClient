package com.example.cui.gankclient.manager;

import android.content.Context;

import com.example.cui.gankclient.bean.MainBean;
import com.example.cui.gankclient.bean.MeiZi;
import com.example.cui.gankclient.http.RetrofitHelper;
import com.example.cui.gankclient.http.RetrofitService;

import io.reactivex.Observable;


/**
 * 管理RetrofitService
 * 提供给p层调用
 */

public class DataManager {
    private RetrofitService mRetrofitService;

    public DataManager(Context context){
        this.mRetrofitService = RetrofitHelper.getInstance(context).getService();
    }

    public Observable<MainBean> getAndroidInfo(String type ,int num, int page){
        return mRetrofitService.getMainInfo(type,num,page);
    }
    public Observable<MeiZi> getMeiZiInfo(int num, int page){
        return mRetrofitService.getMeiZi(num,page);
    }
    public Observable<MainBean> getSearchInfo(String type ,int num, int page){
        return mRetrofitService.getSearchInfo(type,num,page);
    }

}
