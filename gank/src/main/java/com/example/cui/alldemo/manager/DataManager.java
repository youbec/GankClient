package com.example.cui.alldemo.manager;

import android.content.Context;

import com.example.cui.alldemo.bean.MainBean;
import com.example.cui.alldemo.bean.MeiZi;
import com.example.cui.alldemo.http.RetrofitHelper;
import com.example.cui.alldemo.http.RetrofitService;

import io.reactivex.Observable;


/**
 * Created by lynn on 2018/1/18.
 * 管理RetrofitService
 * 提供给p层调用
 *
 * 会有
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
