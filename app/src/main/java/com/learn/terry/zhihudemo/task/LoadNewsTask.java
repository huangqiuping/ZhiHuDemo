package com.learn.terry.zhihudemo.task;

import android.content.Context;
import android.os.AsyncTask;

import com.learn.terry.zhihudemo.adapter.NewsAdapter;
import com.learn.terry.zhihudemo.db.DailyNewsDB;
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
    private IOnFinishRefreshListener mRefreshListener;
    private Context mContext;

    public LoadNewsTask(NewsAdapter newsAdapter) {
        mNewsAdapter = newsAdapter;
    }

    public LoadNewsTask(Context context, NewsAdapter newsAdapter, IOnFinishRefreshListener refreshListener) {
        mContext = context;
        mNewsAdapter = newsAdapter;
        mRefreshListener = refreshListener;
    }

    @Override
    protected ArrayList<News> doInBackground(Void... params) {
        List<News> newsList = null;
        return mNewsFetcher.fetchNewsItems();
    }

    @Override
    protected void onPostExecute(ArrayList<News> newses) {
        mNewsAdapter.refreshNewsList(newses);
        if (mRefreshListener != null) {
            mRefreshListener.onFinishRefresh();
        }
        DailyNewsDB dailyNewsDB = DailyNewsDB.getInstance(mContext);
//        dailyNewsDB.clearNews(DailyNewsDB.NEWS_TYPE_LATEST);

        for (News news : newses) {
            dailyNewsDB.addNews(DailyNewsDB.NEWS_TYPE_LATEST, news);
        }
    }

    public interface IOnFinishRefreshListener {
        void onFinishRefresh();
    }
}
