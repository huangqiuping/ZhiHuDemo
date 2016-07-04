package com.learn.terry.zhihudemo.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.learn.terry.zhihudemo.R;
import com.learn.terry.zhihudemo.adapter.NewsAdapter;
import com.learn.terry.zhihudemo.task.LoadLogoTask;
import com.learn.terry.zhihudemo.task.LoadNewsTask;
import com.learn.terry.zhihudemo.utils.LogUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
                                                               LoadNewsTask.IOnFinishRefreshListener,
                                                               AdapterView.OnItemClickListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mNewList;
    private NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        assert mSwipeRefreshLayout != null;
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
                                                    android.R.color.holo_red_light,
                                                    android.R.color.holo_orange_light,
                                                    android.R.color.holo_blue_bright);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });

        setTitle(getTime());

        mNewList = (ListView) findViewById(R.id.news_list);
        mNewsAdapter = new NewsAdapter(this, R.layout.news_item);
        mNewList.setAdapter(mNewsAdapter);
        mNewList.setOnItemClickListener(this);

//        new LoadNewsTask(mNewsAdapter).execute();

//        String logoSavePath = getExternalFilesDir(null).getAbsolutePath() + File.separator + "logo.jpg";
//        LogUtil.log("path = " + logoSavePath);
//        new LoadLogoTask().execute(logoSavePath);
    }

    private String getTime() {
        Calendar calendar = Calendar.getInstance();

        return SimpleDateFormat.getDateInstance().format(calendar.getTime());
    }

    @Override
    public void onRefresh() {
        LogUtil.log("onRefresh...");
        new LoadNewsTask(mNewsAdapter, MainActivity.this).execute();
    }

    @Override
    public void onFinishRefresh() {
        LogUtil.log("refresh finish...");
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(this, "click " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra("news", mNewsAdapter.getItem(position));

        startActivity(intent);
    }
}
