package com.learn.terry.zhihudemo.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.learn.terry.zhihudemo.R;

public class FavoriteListActivity extends AppCompatActivity {
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle(R.string.favourite);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
