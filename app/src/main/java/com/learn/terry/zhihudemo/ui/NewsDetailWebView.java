package com.learn.terry.zhihudemo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.learn.terry.zhihudemo.utils.LogUtil;

/**
 * Created by terry on 16/7/10.
 */
public class NewsDetailWebView extends WebView {
    private OnScrollChangeListener mOnScrollChangeListener = null;

    public NewsDetailWebView(Context context) {
        super(context);
    }

    public NewsDetailWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewsDetailWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        LogUtil.log("onScrollChanged: l= " + l + ", t= " + t + " oldl = " + oldl + " oldt = " + oldt);

        if (mOnScrollChangeListener != null) {
            mOnScrollChangeListener.onScrollChange(l, t, oldl, oldt);

        }
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        mOnScrollChangeListener = onScrollChangeListener;
    }

    public interface OnScrollChangeListener {
        public void onScrollChange(int l, int t, int oldl, int oldt);
    }

}
