package com.jiun.widgets.utils;

import android.util.Log;

/**
 * @author HJY
 * @date 2018/08/10/10:54
 * <pre>
 *     desc   : 文件描述
 *     version: 当前版本号
 * </pre>
 */
public class LogUtil {
    private static String TAG = "LogUtil";

    public static void i(String msg) {
        Log.i(TAG, wrapMsg(msg));
    }

    public static void e(String msg) {
        Log.e(TAG, wrapMsg(msg));
    }

    private static String wrapMsg(String msg) {
        return "======>> " + msg;
    }
}
