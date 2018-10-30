package com.example.cui.alldemo.http;


import com.example.cui.alldemo.bean.MainBean;
import com.example.cui.alldemo.bean.MeiZi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * 与web api相对应，定义 get post head put delete等接口方法，具体查阅retrofit的api
 * 与rxjava结合和，可定义返回对象为 observable。便于后续的响应式开发
 * <p>
 * 这个只是个接口定义，最后以RetrofitService service = mRetrofit.create(RetrofitService.class);的形式由retrofit来实现和返回
 * retrofit负责将定义网络请求进行接口封装
 */

public interface RetrofitService {

    @GET("data/{type}/{num}/{page}")
    Observable<MainBean> getMainInfo(@Path("type") String type,
                                     @Path("num") int num,
                                        @Path("page") int page);

    @GET("search/query/{type}/category/Android/count/{num}/page/{page}")
    Observable<MainBean> getSearchInfo(@Path("type") String type,
                                     @Path("num") int num,
                                     @Path("page") int page);

    //http://gank.io/api/search/query/listview/category/Android/count/10/page/1

    @GET("data/福利/{num}/{page}")
    Observable<MeiZi> getMeiZi(@Path("num") int num,
                               @Path("page") int page);



}
