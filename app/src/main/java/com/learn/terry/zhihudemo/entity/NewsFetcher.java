package com.learn.terry.zhihudemo.entity;

import com.learn.terry.zhihudemo.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by dvb-sky on 2016/6/30.
 */
public class NewsFetcher {
    public static String ENDPOINT = "http://news-at.zhihu.com/api/4/news/";
    public static String NES_LIST_LATEST = "latest";

    public ArrayList<News> fetchNewsItems() {
        return downloadNewsItems(ENDPOINT + NES_LIST_LATEST);
    }

    private ArrayList<News> downloadNewsItems(String url) {
        ArrayList<News> newsItems = new ArrayList<>();
        String jsonString = getUrl(url);
        parseNewsItems(newsItems, jsonString);

        LogUtil.log("news: " + newsItems.toString());
        return newsItems;
    }

    private void parseNewsItems(ArrayList<News> newsItems, String jsonString) {
        try {
            JSONObject newsContent = new JSONObject(jsonString);
            JSONArray newsArray = newsContent.getJSONArray("stories");
            for (int i=0; i<newsArray.length(); i++) {

                JSONObject newsInJson = newsArray.getJSONObject(i);
                int id = newsInJson.optInt("id");
                String title = newsInJson.optString("title");
                ArrayList<String> imageList = new ArrayList<>();

                if (newsInJson.has("images")) {
                    JSONArray images = newsInJson.getJSONArray("images");
                    LogUtil.log("image count = " + images.length());

                    for (int j=0; j<images.length();j++) {
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

    private String getUrl(String urlSpec) {
        LogUtil.log("get Url = " + urlSpec);

        HttpURLConnection connection = null;
        ByteArrayOutputStream outputStream = null;

        try {
            URL url = new URL(urlSpec);
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            outputStream = new ByteArrayOutputStream();
            InputStream inputStream = connection.getInputStream();

            int bytesRead = 0;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

}
