package com.learn.terry.zhihudemo.task;

import android.os.AsyncTask;
import android.webkit.WebView;

import com.learn.terry.zhihudemo.entity.News;
import com.learn.terry.zhihudemo.entity.NewsDetail;
import com.learn.terry.zhihudemo.entity.NewsFetcher;
import com.learn.terry.zhihudemo.utils.LogUtil;

/**
 * Created by dvb-sky on 2016/7/1.
 */
public class LoadNewsDetailTask extends AsyncTask<News, Void, NewsDetail> {
    private WebView mWebView;
    private String mContent;
    private OnFinishLoadNewsDetailListener mLoadNewsDetailListener;

    public LoadNewsDetailTask(WebView webView) {
        mWebView = webView;
    }

    public LoadNewsDetailTask(OnFinishLoadNewsDetailListener listener) {
        mLoadNewsDetailListener = listener;
    }

    @Override
    protected NewsDetail doInBackground(News... params) {
        return new NewsFetcher().fetchNewsDetail(params[0]);
    }

    @Override
    protected void onPostExecute(final NewsDetail newsDetail) {
//        LogUtil.log(newsDetail.toString());
//        if (mWebView != null) {
////            mWebView.
////            mWebView.loadData(newsDetail.getBody(), "text/html", "UTF-8");
//            String headerImage;
//            if (newsDetail.getImage() == null || newsDetail.getImage() == "") {
//                headerImage = "";
//
//            } else {
//                headerImage = newsDetail.getImage();
//            }
//            StringBuilder sb = new StringBuilder();
//            sb.append("<div class=\"img-wrap\">")
//              .append("<h1 class=\"headline-title\">")
//              .append(newsDetail.getTitle())
//              .append("</h1>")
//              .append("<span class=\"img-source\">")
//              .append(newsDetail.getImage_source())
//              .append("</span>")
//              .append("<img src=\"")
//              .append(headerImage)
//              .append("\" alt=\"\">")
//              .append("<div class=\"img-mask\"></div>");
//            String mNewsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>" +
//                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\"/>" +
//                    newsDetail.getBody().replace("<div class=\"img-place-holder\">", sb.toString());
//            mWebView.loadDataWithBaseURL("file:///android_asset/", mNewsContent, "text/html", "UTF-8", null);
////            LogUtil.log("content = " + mNewsContent);
//            mWebView.loadData(newsDetail.getBody(), "text/html", "UTF-8");
//            mWebView.loadDataWithBaseURL(newsDetail.getCss().get(0), newsDetail.getBody(), "text/html", "UTF-8", null);
//            mWebView.loadUrl(newsDetail.getShare_url());


//        }

        if (mLoadNewsDetailListener != null) {
            mLoadNewsDetailListener.onFinishLoad(newsDetail);
        }
    }

    public interface OnFinishLoadNewsDetailListener {
        void onFinishLoad(NewsDetail newsDetail);
    }
}
