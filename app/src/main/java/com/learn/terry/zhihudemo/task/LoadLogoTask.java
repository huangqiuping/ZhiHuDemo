package com.learn.terry.zhihudemo.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.learn.terry.zhihudemo.R;
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
            if (logUrl == null) {
                return null;
            }

            DiskCache diskCache = DiskCache.getInstance();

            if (diskCache.isFileExistInCache(logUrl)) {
                return null;
            }

            String key = diskCache.addFileToCache(logUrl);
            LogUtil.log("add file <" + logUrl + "> to cache result = " + key);
            SharedPreferences sharedPreferences =
                    mContext.getSharedPreferences(mContext.getString(R.string.preference_file_key),
                                                  Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(mContext.getString(R.string.boot_logo), logUrl);
            editor.apply();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        LogUtil.log("fetch logo finish...");
    }
}
