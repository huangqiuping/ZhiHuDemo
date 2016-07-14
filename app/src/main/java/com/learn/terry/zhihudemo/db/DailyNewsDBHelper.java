package com.learn.terry.zhihudemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Terry on 2016/7/13.
 * email: hqp770@126.com
 */
public class DailyNewsDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "daily_news.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER UNIQUE";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_FAV_NEWS_ENTRIES = "CREATE TABLE " + NewsEntry.TABLE_FAV_NEWS_NAME + " (" +
            NewsEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
            NewsEntry.COLUMN_NEWS_ID + INTEGER_TYPE + COMMA_SEP +
            NewsEntry.COLUMN_NEWS_TITLE + TEXT_TYPE + COMMA_SEP +
            NewsEntry.COLUMN_NEWS_IMAGE + TEXT_TYPE +
            " )";

    private static final String SQL_CREATE_LATEST_NEWS_ENTRIES = "CREATE TABLE " + NewsEntry.TABLE_LATEST_NEWS_NAME + " (" +
            NewsEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
            NewsEntry.COLUMN_NEWS_ID + INTEGER_TYPE + COMMA_SEP +
            NewsEntry.COLUMN_NEWS_TITLE + TEXT_TYPE + COMMA_SEP +
            NewsEntry.COLUMN_NEWS_IMAGE + TEXT_TYPE +
            " )";

    private static final String SQL_DELETE_FAV_ENTRIES = "DROP TABLE IF EXISTS " + NewsEntry.TABLE_FAV_NEWS_NAME;
    private static final String SQL_DELETE_LATEST_ENTRIES = "DROP TABLE IF EXISTS " + NewsEntry.TABLE_LATEST_NEWS_NAME;

    public DailyNewsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAV_NEWS_ENTRIES);
        db.execSQL(SQL_CREATE_LATEST_NEWS_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_FAV_ENTRIES);
        db.execSQL(SQL_DELETE_LATEST_ENTRIES);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
