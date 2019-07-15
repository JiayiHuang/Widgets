package com.jiun.glide;

import android.app.Application;

import com.jiun.glide.utils.ImageLoader;

/**
 * <pre>
 *     @author Jiun
 *     @date  :2019/07/03/14:48
 *     desc   : 文件描述
 *     version: 当前版本号
 * </pre>
 */
public class GlideApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.init(getApplicationContext());
    }
}
