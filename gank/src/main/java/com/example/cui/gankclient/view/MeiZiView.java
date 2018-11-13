package com.example.cui.gankclient.view;

import com.example.cui.gankclient.bean.MeiZi;

/**
 * Created by Cui on 2018/10/7 0007.
 */

public interface MeiZiView  extends View{
    void onSuccess(MeiZi meiZi);
    void onError(String result);
}
