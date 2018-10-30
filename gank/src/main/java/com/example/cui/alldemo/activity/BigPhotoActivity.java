package com.example.cui.alldemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cui.alldemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BigPhotoActivity extends AppCompatActivity {

    @BindView(R.id.bigImage)
    ImageView bigImage;
    public static final String URL = "url";
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_photo);
        ButterKnife.bind(this);


        mUrl = getIntent().getStringExtra(URL);
        Glide.with(this)
                .load(mUrl)
                .centerCrop()
                .crossFade()
                .into(bigImage);
    }
}
