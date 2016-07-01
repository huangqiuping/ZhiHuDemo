package com.learn.terry.zhihudemo.task;

import android.os.AsyncTask;

import com.learn.terry.zhihudemo.entity.Logo;
import com.learn.terry.zhihudemo.entity.NewsFetcher;

/**
 * Created by dvb-sky on 2016/7/1.
 */
public class LoadLogoTask extends AsyncTask<Void, Void, String> {
    @Override
    protected String doInBackground(Void... params) {
        new NewsFetcher().fetchLog(Logo.SIZE_HUGE);

        return null;
    }
}
