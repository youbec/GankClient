package com.example.cui.gankclient.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cui.gankclient.R;
import com.example.cui.gankclient.bean.ResultsBean;

import java.util.List;

/**
 * Created by Cui on 2018/10/7 0007.
 */

public class MainAdapter extends BaseQuickAdapter<ResultsBean,BaseViewHolder> {

    public MainAdapter(@Nullable List<ResultsBean> data) {
        super(R.layout.android_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ResultsBean item) {
        helper.setText(R.id.android_desc,item.getDesc())
                .setText(R.id.android_date,item.getPublishedAt().substring(0,10))
                .setText(R.id.android_who,item.getWho());


        Glide.with(mContext).load(item.getImage())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
              //  .centerCrop()
                .override(90,120)
                .into((ImageView) helper.getView(R.id.android_photo));
    }
}
