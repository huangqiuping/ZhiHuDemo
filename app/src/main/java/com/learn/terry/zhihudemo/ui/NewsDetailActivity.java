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
import com.learn.terry.zhihudemo.task.LoadNewsDetailTask;
import com.learn.terry.zhihudemo.utils.LogUtil;

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailWebView.OnScrollChangeListener {
    private NewsDetailWebView mWebView;
    private ActionBar mActionBar;
    private boolean isFavourite = false;
    private News mNews;
    private DailyNewsDB mDailyNewsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        mNews = (News) getIntent().getSerializableExtra("news");
        LogUtil.log(mNews.toString());

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

        new LoadNewsDetailTask(mWebView).execute(mNews);
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
                    mDailyNewsDB.addNewsToFav(mNews);
                    isFavourite = true;
                } else {
                    item.setIcon(R.drawable.ic_favorite_white);
                    mDailyNewsDB.removeNewsFromFav(mNews);
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
}
