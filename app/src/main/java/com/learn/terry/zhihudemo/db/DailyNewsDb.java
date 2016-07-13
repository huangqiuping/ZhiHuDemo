package com.learn.terry.zhihudemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.learn.terry.zhihudemo.entity.News;

import java.util.ArrayList;

/**
 * Created by Terry on 2016/7/13.
 * email: hqp770@126.com
 */
public class DailyNewsDB {
    public static final int NEWS_TYPE_FAV = 1;
    public static final int NEWS_TYPE_LATEST = 2;

    private DailyNewsDBHelper mDailyNewsDBHelper;
    private SQLiteDatabase mDatabase;
    private static DailyNewsDB sDailyNewsDB;

    private DailyNewsDB(Context context) {
        mDailyNewsDBHelper = new DailyNewsDBHelper(context);
        mDatabase = mDailyNewsDBHelper.getWritableDatabase();
    }

    public static DailyNewsDB getInstance(Context context) {

        if (sDailyNewsDB == null) {
            synchronized (DailyNewsDB.class) {
                if (sDailyNewsDB == null) {
                    sDailyNewsDB = new DailyNewsDB(context);
                }
            }
        }

        return sDailyNewsDB;
    }

    public void addNews(int type, News news) {
        if (news != null) {
            ContentValues values = new ContentValues();
            values.put(NewsEntry.COLUMN_NEWS_ID, news.getId());
            values.put(NewsEntry.COLUMN_NEWS_TITLE, news.getTitle());
            if (news.getImages().size() > 0) {
                values.put(NewsEntry.COLUMN_NEWS_IMAGE, news.getImages().get(0));
            }

            switch (type) {
                case NEWS_TYPE_FAV:
                    mDatabase.insert(NewsEntry.TABLE_FAV_NEWS_NAME, null, values);
                    break;

                case NEWS_TYPE_LATEST:

                    mDatabase.insert(NewsEntry.TABLE_LATEST_NEWS_NAME, null, values);
                    break;

                default:
                    break;
            }
        }
    }

    public void removeNews(int type, News news) {
        if (news != null) {
            switch (type) {
                case NEWS_TYPE_FAV:
                    mDatabase.delete(NewsEntry.TABLE_FAV_NEWS_NAME,
                                     NewsEntry.COLUMN_NEWS_ID + " = ?",
                                     new String[]{news.getId() + ""});
                    break;

                case NEWS_TYPE_LATEST:
                    mDatabase.delete(NewsEntry.TABLE_LATEST_NEWS_NAME,
                                     NewsEntry.COLUMN_NEWS_ID + " = ?",
                                     new String[]{news.getId() + ""});
                    break;

                default:
                    break;
            }
        }
    }

    public ArrayList<News> loadNews(int type) {
        ArrayList<News> newsList = new ArrayList<>();
        Cursor cursor;
        switch (type) {
            case NEWS_TYPE_FAV:
                cursor = mDatabase.query(NewsEntry.TABLE_FAV_NEWS_NAME, null, null, null, null, null, null);
                break;

            case NEWS_TYPE_LATEST:
                cursor = mDatabase.query(NewsEntry.TABLE_LATEST_NEWS_NAME, null, null, null, null, null, null);
                break;

            default:
                return newsList;
        }

        if (cursor.moveToFirst()) {
            do {
                News news = new News();
                news.setId(cursor.getInt(1));
                news.setTitle(cursor.getString(2));
                String imageUrl = cursor.getString(3);
                ArrayList<String> images = new ArrayList<>();
                images.add(imageUrl);
                news.setImages(images);

                newsList.add(news);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return newsList;
    }

    public boolean isFavNews(News news) {
        Cursor cursor = mDatabase.query(NewsEntry.TABLE_FAV_NEWS_NAME,
                                        null,
                                        NewsEntry.COLUMN_NEWS_ID + " = ?",
                                        new String[]{news.getId() + ""},
                                        null,
                                        null,
                                        null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            return false;
        }
    }
}
