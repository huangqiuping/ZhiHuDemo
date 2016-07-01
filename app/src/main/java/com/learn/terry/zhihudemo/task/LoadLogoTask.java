package com.learn.terry.zhihudemo.task;

import android.os.AsyncTask;

import com.learn.terry.zhihudemo.entity.Logo;
import com.learn.terry.zhihudemo.entity.NewsFetcher;
import com.learn.terry.zhihudemo.http.Http;
import com.learn.terry.zhihudemo.utils.LogUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dvb-sky on 2016/7/1.
 */
public class LoadLogoTask extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... params) {
        Logo logo = new NewsFetcher().fetchLog(Logo.SIZE_HUGE);
        byte[] logoData = Http.getBytes(logo.getImg());

        try {
            BufferedOutputStream bufferedOutputStream =
                    new BufferedOutputStream(new FileOutputStream(new File(params[0])));
            bufferedOutputStream.write(logoData);

            bufferedOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        LogUtil.log("logo download finish...");
    }
}
