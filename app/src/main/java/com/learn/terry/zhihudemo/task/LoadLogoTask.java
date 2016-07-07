package com.learn.terry.zhihudemo.task;

import android.content.Context;
import android.os.AsyncTask;

import com.learn.terry.zhihudemo.entity.Logo;
import com.learn.terry.zhihudemo.entity.NewsFetcher;
import com.learn.terry.zhihudemo.http.Http;
import com.learn.terry.zhihudemo.utils.DiskCache;
import com.learn.terry.zhihudemo.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by dvb-sky on 2016/7/1.
 */
public class LoadLogoTask extends AsyncTask<Void, Void, Void> {
    private Context mContext;

    public LoadLogoTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Logo logo = new NewsFetcher().fetchLog(Logo.SIZE_HUGE);

        if (logo != null) {
            String logUrl = logo.getImg();
            DiskCache diskCache = DiskCache.getInstance(mContext);

            if (diskCache.isFileExistInCache(logUrl)) {
                return null;
            }

            boolean result = diskCache.addFileToCache(logUrl);
            LogUtil.log("add file <" + logUrl + "> to cache result = " + result);

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        LogUtil.log("fetch logo finish...");
    }
}
