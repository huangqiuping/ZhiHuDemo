package com.learn.terry.zhihudemo.entity;

import com.google.gson.Gson;
import com.learn.terry.zhihudemo.http.Http;
import com.learn.terry.zhihudemo.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dvb-sky on 2016/6/30.
 */
public class NewsFetcher {
    public static String ENDPOINT = "http://news-at.zhihu.com/api/4/";
    public static String NEWS = "news";
    public static String START_IMAGE = "start-image";
    public static String NES_LIST_LATEST = "latest";

    public ArrayList<News> fetchNewsItems() {
        return downloadNewsItems(ENDPOINT + NEWS + "/" + NES_LIST_LATEST);
    }

    public NewsDetail fetchNewsDetail(News news) {
        NewsDetail newsDetail = null;

        String url = ENDPOINT + NEWS + "/" + news.getId();
        String jsonString = Http.getUrl(url);
        newsDetail = parseNewsDetail(jsonString);
        return newsDetail;
    }

    public String fetchNewCss(NewsDetail newsDetail) {
        ArrayList<String> css = newsDetail.getCss();
        if (css.size() > 0) {
            return Http.getUrl(css.get(0));
        } else {
            return null;
        }
    }

    public Logo fetchLog(int size) {
        Logo logo = null;
        String url = ENDPOINT + START_IMAGE + "/" + convertSizeToString(size);
        String jsonString = Http.getUrl(url);
        logo = parseLogo(jsonString);

        return logo;
    }

    private Logo parseLogo(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Logo.class);
    }

    private String convertSizeToString(int size) {
        String string = "720*1184";

        switch (size) {
            case Logo.SIZE_SMALL:
                string = "320*432";
                break;

            case Logo.SIZE_MIDDLE:
                string = "480*728";
                break;

            case Logo.SIZE_LARGE:
                string = "720*1184";
                break;

            case Logo.SIZE_HUGE:
                string = "1080*1776";
                break;
        }

        return string;
    }


    private NewsDetail parseNewsDetail(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, NewsDetail.class);
    }

    private ArrayList<News> downloadNewsItems(String url) {
        ArrayList<News> newsItems = new ArrayList<>();
        String jsonString = Http.getUrl(url);
        parseNewsItems(newsItems, jsonString);

        LogUtil.log("news: " + newsItems.toString());
        return newsItems;
    }

    private void parseNewsItems(ArrayList<News> newsItems, String jsonString) {
        try {
            JSONObject newsContent = new JSONObject(jsonString);
            JSONArray newsArray = newsContent.getJSONArray("stories");
            for (int i = 0; i < newsArray.length(); i++) {

                JSONObject newsInJson = newsArray.getJSONObject(i);
                int id = newsInJson.optInt("id");
                String title = newsInJson.optString("title");
                ArrayList<String> imageList = new ArrayList<>();

                if (newsInJson.has("images")) {
                    JSONArray images = newsInJson.getJSONArray("images");

                    for (int j = 0; j < images.length(); j++) {
                        imageList.add((String) images.get(j));
                    }
                }

                News news = new News(id, title, imageList);
                newsItems.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
