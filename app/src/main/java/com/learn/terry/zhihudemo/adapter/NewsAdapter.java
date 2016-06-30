package com.learn.terry.zhihudemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.learn.terry.zhihudemo.R;
import com.learn.terry.zhihudemo.entity.News;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dvb-sky on 2016/6/30.
 */
public class NewsAdapter extends ArrayAdapter<News> {
    private LayoutInflater mLayoutInflater;
    private int mResource;

    private ImageLoader mImageLoader = ImageLoader.getInstance();
    private DisplayImageOptions mOptions =
            new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.no_image)
                    .showImageOnFail(R.drawable.no_image)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .build();

    public NewsAdapter(Context context, int resource) {
        super(context, resource);
        mLayoutInflater = LayoutInflater.from(context);
        mResource = resource;
    }

    public NewsAdapter(Context context, int resource, List<News> objects) {
        super(context, resource, objects);
        mLayoutInflater = LayoutInflater.from(context);
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(mResource, null);
            holder = new ViewHolder();
            holder.newsTitle = (TextView) convertView.findViewById(R.id.news_title);
            holder.newsImage = (ImageView) convertView.findViewById(R.id.news_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        News news = getItem(position);
        holder.newsTitle.setText(news.getTitle());

        ArrayList<String> images = news.getImages();
        if (images.size() > 0) {
            mImageLoader.displayImage(images.get(0), holder.newsImage, mOptions);
        }

        return convertView;
    }

    private class ViewHolder {
        ImageView newsImage;
        TextView newsTitle;
    }

    public void refreshNewsList(ArrayList<News> newses) {
        clear();
        addAll(newses);
        notifyDataSetChanged();
    }
}
