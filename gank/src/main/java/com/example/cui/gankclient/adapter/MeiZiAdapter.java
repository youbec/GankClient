package com.example.cui.gankclient.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cui.gankclient.R;
import com.example.cui.gankclient.bean.MeiZi;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Cui on 2018/10/7 0007.
 */

public class MeiZiAdapter extends BaseQuickAdapter<MeiZi.ResultsBean, BaseViewHolder> {


    @BindView(R.id.android_img)
    ImageView androidImg;

    public MeiZiAdapter(@Nullable List<MeiZi.ResultsBean> data) {
        super(R.layout.meizi_item, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, MeiZi.ResultsBean item) {

        helper.addOnClickListener(R.id.item);

        Glide.with(mContext).load(item.getUrl()).centerCrop().crossFade().into((ImageView) helper.getView(R.id.android_img));
    }


}