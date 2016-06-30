package com.learn.terry.zhihudemo;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by dvb-sky on 2016/6/29.
 */
public class ZhiHuApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(this);
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
