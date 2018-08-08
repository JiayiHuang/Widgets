package com.jy.widgets.utils;

import android.app.Application;
import android.widget.Toast;

/**
 * @author HJY
 * @date 2018/08/08/13:47
 * <pre>
 *     desc   : 文件描述
 *     version: 当前版本号
 * </pre>
 */
public class ToastUtils {
    private static Toast mToast;
    private static Application mApplication;

    public static void setApplication(Application application) {
        mApplication = application;
    }

    public static void showShort(String msg) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(mApplication, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
