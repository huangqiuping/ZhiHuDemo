package com.learn.terry.zhihudemo.utils;

import android.content.Context;

import com.learn.terry.zhihudemo.http.Http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by dvb-sky on 2016/7/4.
 */
public class DiskCache {

    private static final int DISK_CACHE_INDEX = 0;
    private DiskLruCache mDiskLruCache;

    private static DiskCache sDiskCache;

    private DiskCache() {
    }

    public static DiskCache getInstance(Context context) {
        if (sDiskCache == null) {
            synchronized (DiskCache.class) {
                if (sDiskCache == null) {
                    sDiskCache = new DiskCache();
                }
            }
        }
        return sDiskCache;
    }

    public synchronized void init(File cacheDir, long maxByteSize) {
        try {
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, 1, 1, maxByteSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean addFileToCache(String data) {
        LogUtil.log("try to add file <" + data + "> to cache!");
        OutputStream outputStream = null;
        if (isFileExistInCache(data)) {
            return true;
        }

        String key = hashKeyForDisk(data);
        LogUtil.log("cache key = " + key);

        try {
            final DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
                if (data.startsWith("http://") || data.startsWith("https://")) {
                    if (Http.downloadUrlToStream(data, outputStream)) {
                        LogUtil.log("download <" + data + "> success.");
                        editor.commit();
                    } else {
                        LogUtil.log("download <" + data + "> failed.");
                        editor.abort();
                    }
                }

                mDiskLruCache.flush();

                return true;
            } else {
                LogUtil.log("get editor failed!!!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;

    }

    public void addFileByInputStreamToCache(String data, InputStream inputStream) {
        if (data == null || inputStream == null) {
            return;
        }

        if (mDiskLruCache != null) {
            final String key = hashKeyForDisk(data);
            OutputStream outputStream = null;
            try {
                DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                if (snapshot == null) {
                    final DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
                        byte[] buffer = new byte[4096];
                        int readLength;
                        while ((readLength = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, readLength);
                        }
                    }
                } else {
                    snapshot.getInputStream(DISK_CACHE_INDEX).close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public boolean isFileExistInCache(String data) {
        String key = hashKeyForDisk(data);
        DiskLruCache.Snapshot snapshot = null;

        try {
            snapshot = mDiskLruCache.get(key);
            return snapshot != null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }

        return false;
    }

    public InputStream getFileInputStreamFromCache(String data) {
        InputStream inputStream = null;
        if (mDiskLruCache != null) {
            final String key = hashKeyForDisk(data);
            try {
                final DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                if (snapshot == null) {
                    return null;
                }

                inputStream = snapshot.getInputStream(DISK_CACHE_INDEX);
                return inputStream;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public OutputStream getFileOutputStreamFromCache(String data) {
        if (getFileInputStreamFromCache(data) != null) {
            return null;
        }

        String key = hashKeyForDisk(data);
        try {
            final DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            return editor.newOutputStream(DISK_CACHE_INDEX);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
