package com.learn.terry.zhihudemo.db;

import android.provider.BaseColumns;

/**
 * Created by Terry on 2016/7/13.
 * email: hqp770@126.com
 */
public class NewsEntry implements BaseColumns {
    public static final String TABLE_FAV_NEWS_NAME = "news_fav";
    public static final String TABLE_LATEST_NEWS_NAME = "news_latest";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NEWS_ID = "news_id";
    public static final String COLUMN_NEWS_TITLE = "news_title";
    public static final String COLUMN_NEWS_IMAGE = "news_image";
}
