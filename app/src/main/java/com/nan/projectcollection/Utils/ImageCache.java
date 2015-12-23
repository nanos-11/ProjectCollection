package com.nan.projectcollection.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

public class ImageCache {
    public static final float newHeight = 100f; // 缩放高度
    public static final float newWidth = 100f; // 缩放宽带
    private static final int SOFT_CACHE_SIZE = 15; // 软引用缓存容量
    private static ImageCache mImageCache = new ImageCache();
    private static LinkedHashMap<String, SoftReference<BitmapDrawable>> mSoftCache; // 软引用缓存
    private LruCache<String, BitmapDrawable> mLruCache;

    private ImageCache() {
        // 获取系统分配给每个应用程序的最大内存
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int mCacheSize = maxMemory / 8;
        // 给LruCache分配1/8
        mLruCache = new LruCache<String, BitmapDrawable>(mCacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                Bitmap bitmap = value.getBitmap();
                if (bitmap != null) {
                    return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                }
                return 0;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key,
                                        BitmapDrawable oldValue, BitmapDrawable newValue) {
                if (oldValue != null)
                    // 硬引用缓存容量满的时候，会根据LRU算法把最近没有被使用的图片转入此软引用缓存
                    mSoftCache.put(key, new SoftReference<BitmapDrawable>(
                            oldValue));
            }
        };
        mSoftCache = new LinkedHashMap<String, SoftReference<BitmapDrawable>>(
                SOFT_CACHE_SIZE, 0.75f, true) {
            private static final long serialVersionUID = 6040103833179403725L;

            @Override
            protected boolean removeEldestEntry(
                    Entry<String, SoftReference<BitmapDrawable>> eldest) {
                if (size() > SOFT_CACHE_SIZE) {
                    return true;
                }
                return false;
            }
        };
    }

    /**
     * 获取ImageCache的一个实例
     *
     * @return
     */
    public static ImageCache getInstance() {
        return mImageCache;
    }

    public static Bitmap compressImg(String path) {


        // 比例压缩
        Options newOpts = new Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap image = BitmapFactory.decodeFile(path, newOpts);// 此时返回bm为空
        newOpts.inJustDecodeBounds = false;

        // 比例压缩
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 计算缩放比例
        float hh = newWidth;// 这里设置高度为100f
        float ww = newHeight;// 这里设置宽度为100f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例

        // 得到新的图片
        image = BitmapFactory.decodeFile(path, newOpts);
        return image;
    }

    /**
     * 缓存图片到内存
     *
     * @param key    Bitmap的关键字
     * @param bitmap Bitmap对象
     */
    public void put(String key, BitmapDrawable drawable) {
        // if(getBitmapFromMemory(key)==null&&drawable!=null)
        // mLruCache.put(key, drawable);
        if (drawable != null) {
            synchronized (mLruCache) {
                mLruCache.put(key, drawable);
            }
        }
    }

    /**
     * 从内存获取指定的bitmap
     *
     * @param key 要获取的Bitmap的key
     * @return 如果内存中没有指定对象，返回null，否则返回指定key的对象
     */
    public BitmapDrawable getBitmapFromMemory(String key) {
        // synchronized (this) {
        // BitmapDrawable bitmapDrawable = mLruCache.get(key);
        // if(bitmapDrawable != null &&
        // !bitmapDrawable.getBitmap().isRecycled()){
        // return mLruCache.get(key);
        // }
        // return null;
        // }

        BitmapDrawable bitmap;
        // 先从硬引用缓存中获取
        synchronized (mLruCache) {
            bitmap = mLruCache.get(key);
            if (bitmap != null) {
                // 如果找到的话，把元素移到LinkedHashMap的最前面，从而保证在LRU算法中是最后被删除
                mLruCache.remove(key);
                mLruCache.put(key, bitmap);
                return bitmap;
            }
        }
        // 如果硬引用缓存中找不到，到软引用缓存中找
        synchronized (mSoftCache) {
            SoftReference<BitmapDrawable> bitmapReference = mSoftCache.get(key);
            if (bitmapReference != null) {
                bitmap = bitmapReference.get();
                if (bitmap != null) {
                    // 将图片移回硬缓存
                    mLruCache.put(key, bitmap);
                    mSoftCache.remove(key);
                    return bitmap;
                } else {
                    mSoftCache.remove(key);
                }
            }
        }
        return null;
    }

    public void removeBitmap(String key) {
        synchronized (this) {
            if (getBitmapFromMemory(key) != null
                    && !getBitmapFromMemory(key).getBitmap().isRecycled()) {
                BitmapDrawable bitmapFromMemory = getBitmapFromMemory(key);
                mLruCache.remove(key);
                bitmapFromMemory.getBitmap().recycle();
                bitmapFromMemory = null;
            }
        }
    }
}
