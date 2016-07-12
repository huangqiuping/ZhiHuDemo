package com.learn.terry.zhihudemo.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.learn.terry.zhihudemo.R;
import com.learn.terry.zhihudemo.entity.News;
import com.learn.terry.zhihudemo.task.LoadNewsDetailTask;
import com.learn.terry.zhihudemo.utils.LogUtil;

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailWebView.OnScrollChangeListener {
    private NewsDetailWebView mWebView;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        News news = (News) getIntent().getSerializableExtra("news");
        LogUtil.log(news.toString());

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        mWebView = (NewsDetailWebView) findViewById(R.id.web_view);
        assert mWebView != null;
        mWebView.setOnScrollChangeListener(this);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

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
        LogUtil.log("onScrollChange: l = " + l + ", t = " + t + ", oldl = " + oldl + ", oldt = " + oldt);
        if (oldt < t) {
            updateActionBar(false);
        } else {
            updateActionBar(true);
        }
    }

    private void updateActionBar(boolean isShow) {
        if (mActionBar == null) {
            return;
        }
        if (isShow) {
            mActionBar.show();
        } else {
//            mActionBar.hide();
        }
    }
}
