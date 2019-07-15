package com.jiun.glide.utils;

import android.content.Context;

import com.bumptech.glide.MemoryCategory;

/**
 * <pre>
 *     @author Jiun
 *     @date  :2019/07/03/14:47
 *     desc   : 文件描述
 *     version: 当前版本号
 * </pre>
 */
public class ImageLoader {
    public static int CACHE_IMAGE_SIZE = 250;
    public static void init(final Context context) {
        init(context, CACHE_IMAGE_SIZE);
    }
    public static void init(final Context context, int cacheSizeInM) {
        init(context, cacheSizeInM, MemoryCategory.NORMAL);
    }
    public static void init(final Context context, int cacheSizeInM, MemoryCategory
            memoryCategory) {
        init(context, cacheSizeInM, memoryCategory, true);
    }

    /**
     * @param context 上下文
     * @param cacheSizeInM Glide 默认磁盘缓存最大容量 250MB
     * @param memoryCategory 调整内存缓存的大小 LOW(0.5f) ／ NORMAL(1f) ／ HIGH(1.5f);
     * @param isInternalCD true 磁盘缓存到应用的内部目录 / false 磁盘缓存到外部存
     */
    public static void init(final Context context, int cacheSizeInM, MemoryCategory
            memoryCategory, boolean isInternalCD) {
        ImageLoader.context = context;
        GlobalConfig.init(context, cacheSizeInM, memoryCategory, isInternalCD);
    }
}
