package com.example.cui.gankclient.view;

import com.example.cui.gankclient.bean.MainBean;

/**
 * Created by Cui on 2018/10/6 0006.
 */

public interface MainBeanView extends View{

    void onSuccess(MainBean mainBean);
    void onError(String result);

}
