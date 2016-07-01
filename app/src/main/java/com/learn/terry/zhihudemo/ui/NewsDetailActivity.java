package com.learn.terry.zhihudemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.learn.terry.zhihudemo.R;
import com.learn.terry.zhihudemo.entity.News;
import com.learn.terry.zhihudemo.task.LoadNewsDetailTask;
import com.learn.terry.zhihudemo.utils.LogUtil;

public class NewsDetailActivity extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        News news = (News) getIntent().getSerializableExtra("news");
        LogUtil.log(news.toString());

        mWebView = (WebView) findViewById(R.id.web_view);

        configWebView(mWebView);

        new LoadNewsDetailTask(mWebView).execute(news);
    }

    private void configWebView(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
    }
}
