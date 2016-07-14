package com.learn.terry.zhihudemo.ui;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.learn.terry.zhihudemo.R;
import com.learn.terry.zhihudemo.db.DailyNewsDB;
import com.learn.terry.zhihudemo.entity.News;
import com.learn.terry.zhihudemo.entity.NewsDetail;
import com.learn.terry.zhihudemo.task.LoadNewsDetailTask;
import com.learn.terry.zhihudemo.utils.LogUtil;

import java.io.Serializable;

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailWebView.OnScrollChangeListener, LoadNewsDetailTask.OnFinishLoadNewsDetailListener{
    private NewsDetailWebView mWebView;
    private ActionBar mActionBar;
    private boolean isFavourite = false;
    private News mNews;
    private DailyNewsDB mDailyNewsDB;
    private NewsDetail mNewsDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        mNews = (News) getIntent().getSerializableExtra("news");
//        LogUtil.log(mNews.toString());

//        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(toolbar);

        mWebView = (NewsDetailWebView) findViewById(R.id.web_view);
        assert mWebView != null;
        mWebView.setOnScrollChangeListener(this);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDailyNewsDB = DailyNewsDB.getInstance(this);
        isFavourite = mDailyNewsDB.isFavNews(mNews);

        configWebView(mWebView);

//        new LoadNewsDetailTask(mWebView).execute(mNews);
        if (savedInstanceState != null) {
            NewsDetail newsDetail = (NewsDetail) savedInstanceState.getSerializable("news_detail");
            if (newsDetail != null) {
                mNewsDetail = newsDetail;
                showNewsDetail(newsDetail);
                return;
            }
        }
        new LoadNewsDetailTask(this).execute(mNews);
    }

    private void configWebView(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        LogUtil.log("onCreateOptionsMenu...");
        getMenuInflater().inflate(R.menu.news_detail_menu, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_STREAM, "Hello");

        shareActionProvider.setShareIntent(intent);

        MenuItem favItem = menu.findItem(R.id.action_favourite);

        if (isFavourite) {
            favItem.setIcon(R.drawable.ic_favorite_red);
        } else {
            favItem.setIcon(R.drawable.ic_favorite_white);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_favourite:

                if (!isFavourite) {
                    item.setIcon(R.drawable.ic_favorite_red);
//                    mDailyNewsDB.addNewsToFav(mNews);
                    mDailyNewsDB.addNews(DailyNewsDB.NEWS_TYPE_FAV, mNews);
                    isFavourite = true;
                } else {
                    item.setIcon(R.drawable.ic_favorite_white);
//                    mDailyNewsDB.removeNewsFromFav(mNews);
                    mDailyNewsDB.removeNews(DailyNewsDB.NEWS_TYPE_FAV, mNews);
                    isFavourite = false;
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
            mActionBar.hide();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        LogUtil.log("onSaveInstanceState...");

        outState.putSerializable("news_detail", mNewsDetail);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onFinishLoad(NewsDetail newsDetail) {
        mNewsDetail = newsDetail;

        showNewsDetail(newsDetail);
    }

    private void showNewsDetail(NewsDetail newsDetail) {
        if (mWebView != null) {
            String headerImage;
            if (newsDetail.getImage() == null || newsDetail.getImage() == "") {
                headerImage = "";

            } else {
                headerImage = newsDetail.getImage();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("<div class=\"img-wrap\">")
                    .append("<h1 class=\"headline-title\">")
                    .append(newsDetail.getTitle())
                    .append("</h1>")
                    .append("<span class=\"img-source\">")
                    .append(newsDetail.getImage_source())
                    .append("</span>")
                    .append("<img src=\"")
                    .append(headerImage)
                    .append("\" alt=\"\">")
                    .append("<div class=\"img-mask\"></div>");
            String mNewsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\"/>" +
                    newsDetail.getBody().replace("<div class=\"img-place-holder\">", sb.toString());
            mWebView.loadDataWithBaseURL("file:///android_asset/", mNewsContent, "text/html", "UTF-8", null);
        }
    }
}
