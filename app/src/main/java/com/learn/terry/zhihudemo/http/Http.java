package com.learn.terry.zhihudemo.http;

import com.learn.terry.zhihudemo.utils.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dvb-sky on 2016/7/1.
 */
public class Http {
    public static String getUrl(String urlSpec) {
        byte[] data = getBytes(urlSpec);
        if (data != null) {
            return new String(data);
        } else {
            return null;
        }
    }

    public static byte[] getBytes(String urlSpec) {
        LogUtil.log("get Url = " + urlSpec);

        HttpURLConnection connection = null;
        ByteArrayOutputStream outputStream = null;

        try {
            URL url = new URL(urlSpec);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(5000);
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

            return outputStream.toByteArray();

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
