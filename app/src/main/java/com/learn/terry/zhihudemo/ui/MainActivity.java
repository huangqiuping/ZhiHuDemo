package com.learn.terry.zhihudemo.ui;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.learn.terry.zhihudemo.R;
import com.learn.terry.zhihudemo.adapter.NewsAdapter;
import com.learn.terry.zhihudemo.task.LoadNewsTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mNewList;

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
        NewsAdapter adapter = new NewsAdapter(this, R.layout.news_item);
        mNewList.setAdapter(adapter);

        new LoadNewsTask(adapter).execute();

    }

    private String getTime() {
        Calendar calendar = Calendar.getInstance();

        return SimpleDateFormat.getDateInstance().format(calendar.getTime());
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
}
