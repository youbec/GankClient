package com.example.cui.gankclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.cui.gankclient.R;
import com.example.cui.gankclient.utils.BuglyUtil;
import com.example.cui.gankclient.utils.ToastUtil;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.cui.gankclient.utils.DataCleanManager.clearAllCache;
import static com.example.cui.gankclient.utils.DataCleanManager.getTotalCacheSize;

public class SetActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.size)
    SuperTextView size;
    @BindView(R.id.version)
    SuperTextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);

        try {
            String totalCacheSize = getTotalCacheSize(this);
            size.setRightString(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String appVersionName = AppUtils.getAppVersionName();
        int appVersionCode = AppUtils.getAppVersionCode();
        version.setRightString(appVersionName+appVersionCode);
    }

    @OnClick({R.id.back, R.id.size, R.id.version})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.size:
                clearAllCache(this);
                size.setRightString("0K");
                ToastUtil.showToast(this, "缓存清理成功");
                break;
            case R.id.version:

                Beta.upgradeStateListener = new UpgradeStateListener() {
                    @Override
                    public void onUpgradeFailed(boolean b) {
                        ToastUtils.showShort("检查新版本失败，请稍后重试");
                    }

                    @Override
                    public void onUpgradeSuccess(boolean b) {

                    }

                    @Override
                    public void onUpgradeNoVersion(boolean b) {
                        ToastUtils.showShort("你已经是最新版了");
                    }

                    @Override
                    public void onUpgrading(boolean b) {

                    }

                    @Override
                    public void onDownloadCompleted(boolean b) {

                    }
                };

                BuglyUtil.checkUpdate(true,false);

                break;
        }
    }
}
