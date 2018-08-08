package com.jy.widgets;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author HJY
 * @date 2018/08/08/17:20
 * <pre>
 *     desc   : 文件描述
 *     version: 当前版本号
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG = this.getClass().getSimpleName();
    protected Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = this;
        setContentView(getLayoutId());
        initViews();
    }

    /**
     * init views
     */
    protected abstract void initViews();

    /**
     * get layout file id
     *
     * @return layout Id
     */
    protected abstract int getLayoutId();
}
