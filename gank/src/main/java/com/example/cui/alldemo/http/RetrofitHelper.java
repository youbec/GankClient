package com.example.cui.alldemo.http;

import android.content.Context;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lynn on 2018/1/18.
 * 负责 Retrofit的build的参数设置，包括baseurl
 * httpclient 参数设置
 * Gsonconverter
 * 如果要集成rxjava还需要设置 addCallAdapterFactory
 *
 * 对外提供 getService()的方法，返回 retrofit service的具体实现
 *
 * 
 */

public class RetrofitHelper {

    private Context mContext;

    OkHttpClient client = new OkHttpClient();
    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private static RetrofitHelper instance = null;
    private Retrofit mRetrofit;



    private RetrofitHelper(Context context) {
        mContext = context;
        init();

    }

    public static RetrofitHelper getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitHelper(context);
        }
        return instance;
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public RetrofitService getService() {
        return mRetrofit.create(RetrofitService.class);
    }

}
