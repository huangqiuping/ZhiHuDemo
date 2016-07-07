package com.learn.terry.zhihudemo;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.learn.terry.zhihudemo.utils.DiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

/**
 * Created by dvb-sky on 2016/6/29.
 */
public class ZhiHuApplication extends Application {
    private static final int DISK_CACHE_SIZE = 10 * 1024 * 1024;    // SD 32 MB

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(this);
        initBitmapDiskCache(this);
    }

    private void initBitmapDiskCache(Context context) {
        // SD缓存
        File cacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                                         File.separator + "cacheDir");

        DiskCache.getInstance().init(context, cacheDir, DISK_CACHE_SIZE);
    }

    private void initImageLoader(Context context) {
        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration.Builder(context)
                        .denyCacheImageMultipleSizesInMemory()
                        .threadPriority(Thread.NORM_PRIORITY - 2)
                        .tasksProcessingOrder(QueueProcessingType.LIFO)
                        .build();
        ImageLoader.getInstance().init(config);

    }
}
