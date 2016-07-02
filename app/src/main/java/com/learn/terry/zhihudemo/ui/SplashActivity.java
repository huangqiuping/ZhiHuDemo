package com.learn.terry.zhihudemo.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.learn.terry.zhihudemo.R;
import com.learn.terry.zhihudemo.utils.LogUtil;

import java.io.File;
import java.io.IOException;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        initLogo();

        delayToShowHome(2000);
    }

    private void delayToShowHome(int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, delay);
    }

    private void initLogo() {
        ImageView startLogo = (ImageView) findViewById(R.id.start_logo);
        String logoSavePath = getExternalFilesDir(null).getAbsolutePath() + File.separator + "logo.jpg";
        File logoFile = new File(logoSavePath);
        if (false && logoFile.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(logoSavePath, options);
            LogUtil.log("logo width = " + options.outWidth + " height = " + options.outHeight);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            LogUtil.log("screen width = " + displayMetrics.widthPixels + " height = " + displayMetrics.heightPixels);

            options.inJustDecodeBounds = false;
            options.inSampleSize =
                    calculateInSampleSize(options, displayMetrics.widthPixels, displayMetrics.heightPixels);

            LogUtil.log("in sample size = " + options.inSampleSize);
            startLogo.setImageBitmap(BitmapFactory.decodeFile("file://android_asset/logo.jpg", options));
        } else {
            assert startLogo != null;
            try {
                startLogo.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("logo.jpg")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
