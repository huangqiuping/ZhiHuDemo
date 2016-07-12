package com.learn.terry.zhihudemo.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.learn.terry.zhihudemo.R;
import com.learn.terry.zhihudemo.entity.News;
import com.learn.terry.zhihudemo.task.LoadNewsDetailTask;
import com.learn.terry.zhihudemo.utils.LogUtil;

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailWebView.OnScrollChangeListener{
    private NewsDetailWebView mWebView;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        News news = (News) getIntent().getSerializableExtra("news");
        LogUtil.log(news.toString());

        mWebView = (NewsDetailWebView) findViewById(R.id.web_view);
        assert mWebView != null;
        mWebView.setOnScrollChangeListener(this);
        mActionBar = getSupportActionBar();

        configWebView(mWebView);

        new LoadNewsDetailTask(mWebView).execute(news);
    }

    private void configWebView(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
    }

    @Override
    public void onScrollChange(int l, int t, int oldl, int oldt) {
        if (oldt < t) {
            mActionBar.hide();
        } else {
            mActionBar.show();
        }
    }
}
