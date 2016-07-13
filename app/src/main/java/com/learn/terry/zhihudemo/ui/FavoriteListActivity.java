package com.learn.terry.zhihudemo.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.learn.terry.zhihudemo.R;
import com.learn.terry.zhihudemo.adapter.NewsAdapter;
import com.learn.terry.zhihudemo.db.DailyNewsDB;

public class FavoriteListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ActionBar mActionBar;
    private NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle(R.string.favourite);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        ListView listView = (ListView) findViewById(R.id.fav_list);
        mNewsAdapter = new NewsAdapter(this,
                                       R.layout.news_item,
                                       DailyNewsDB.getInstance(this).loadNews(DailyNewsDB.NEWS_TYPE_FAV));
        assert listView != null;
        listView.setAdapter(mNewsAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mNewsAdapter.refreshNewsList(DailyNewsDB.getInstance(this).loadNews(DailyNewsDB.NEWS_TYPE_FAV));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra("news", mNewsAdapter.getItem(position));

        startActivity(intent);
    }
}
