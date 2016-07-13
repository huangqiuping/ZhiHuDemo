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

    public void addNewsToFav(News news) {
        if (news != null) {
            ContentValues values = new ContentValues();
            values.put(NewsEntry.COLUMN_NEWS_ID, news.getId());
            values.put(NewsEntry.COLUMN_NEWS_TITLE, news.getTitle());
            if (news.getImages().size() > 0) {
                values.put(NewsEntry.COLUMN_NEWS_IMAGE, news.getImages().get(0));
            }

            mDatabase.insert(NewsEntry.TABLE_NAME, null, values);
        }
    }

    public void removeNewsFromFav(News news) {
        if (news != null) {

            mDatabase.delete(NewsEntry.TABLE_NAME, NewsEntry.COLUMN_NEWS_ID + " = ?", new String[]{news.getId() + ""});
        }
    }

    public ArrayList<News> loadFavNews() {
        ArrayList<News> favNews = new ArrayList<>();
        Cursor cursor = mDatabase.query(NewsEntry.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                News news = new News();
                news.setId(cursor.getInt(1));
                news.setTitle(cursor.getString(2));
                String imageUrl = cursor.getString(3);
                ArrayList<String> images = new ArrayList<>();
                images.add(imageUrl);
                news.setImages(images);

                favNews.add(news);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return favNews;
    }

    public boolean isFavNews(News news) {
        Cursor cursor = mDatabase.query(NewsEntry.TABLE_NAME,
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
