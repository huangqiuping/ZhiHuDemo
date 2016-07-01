package com.learn.terry.zhihudemo.entity;

/**
 * Created by dvb-sky on 2016/7/1.
 */
public class Logo {
    public static final int SIZE_SMALL = 1;
    public static final int SIZE_MIDDLE = 2;
    public static final int SIZE_LARGE = 3;
    public static final int SIZE_HUGE = 4;
    /**
     * text : Teddy Kelley
     * img : https://pic1.zhimg.com/87c8ac1ca82307ed7679c29cbbcee4a3.jpg
     */

    private String text;
    private String img;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
