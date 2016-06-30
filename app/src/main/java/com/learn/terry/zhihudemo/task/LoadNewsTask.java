package com.learn.terry.zhihudemo.task;

import android.os.AsyncTask;

import com.learn.terry.zhihudemo.adapter.NewsAdapter;
import com.learn.terry.zhihudemo.entity.News;
import com.learn.terry.zhihudemo.entity.NewsFetcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dvb-sky on 2016/6/30.
 */
public class LoadNewsTask extends AsyncTask<Void, Void, ArrayList<News>> {
    private NewsAdapter mNewsAdapter;
    private NewsFetcher mNewsFetcher = new NewsFetcher();

    public LoadNewsTask(NewsAdapter newsAdapter) {
        mNewsAdapter = newsAdapter;
    }

    @Override
    protected ArrayList<News> doInBackground(Void... params) {
        List<News> newsList = null;
        return mNewsFetcher.fetchNewsItems();
    }

    @Override
    protected void onPostExecute(ArrayList<News> newses) {
        mNewsAdapter.refreshNewsList(newses);
    }
}
