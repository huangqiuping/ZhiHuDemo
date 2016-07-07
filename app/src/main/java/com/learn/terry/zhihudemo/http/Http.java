package com.learn.terry.zhihudemo.http;

import com.learn.terry.zhihudemo.utils.LogUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        InputStream inputStream = null;

        try {
            URL url = new URL(urlSpec);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(5000);
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            outputStream = new ByteArrayOutputStream();
            inputStream = connection.getInputStream();

            int bytesRead = 0;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            try {
                if (outputStream != null) {
                    outputStream.close();
                }

                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    public static boolean downloadUrlToStream(String urlSpec, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;

        try {
            URL url = new URL(urlSpec);
            urlConnection = (HttpURLConnection) url.openConnection();
            bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            bufferedOutputStream = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(b);
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            try {
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }

                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

}
