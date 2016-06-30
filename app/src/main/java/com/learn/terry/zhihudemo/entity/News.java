package com.learn.terry.zhihudemo.entity;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dvb-sky on 2016/6/30.
 */
public class News implements Serializable {
    private int mId;
    private String mTitle;
    private ArrayList<String> mImages = new ArrayList<>();

    public News() {
    }

    public News(int id, String title, ArrayList images) {
        mId = id;
        mTitle = title;
        mImages = images;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public ArrayList<String> getImages() {
        return mImages;
    }

    public void setImages(ArrayList<String> images) {
        if (images != null) {
            mImages = images;
        }
    }

    @Override
    public String toString() {
        return "News{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mImages=" + mImages +
                '}';
    }
}
