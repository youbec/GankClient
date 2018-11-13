package com.example.cui.gankclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cui.gankclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.wangyuwei.particleview.ParticleView;

public class SplashActivity extends AppCompatActivity {


    @BindView(R.id.pv_github)
    ParticleView pvGithub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


        pvGithub.startAnim();
        pvGithub.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                Intent intent = new Intent(SplashActivity.this, MainTabActivity.class);
                SplashActivity.this.startActivity(intent);
                finish();
            }
        });

    }
}
