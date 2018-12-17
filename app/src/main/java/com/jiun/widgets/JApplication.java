package com.jiun.widgets;

import android.app.Application;

import com.jiun.widgets.utils.ToastUtils;

/**
 * @author HJY
 * @date 2018/08/08/16:05
 * <pre>
 *     desc   : 文件描述
 *     version: 当前版本号
 * </pre>
 */
public class JApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.setApplication(this);
    }
}
