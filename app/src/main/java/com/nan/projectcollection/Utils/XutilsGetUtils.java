package com.nan.projectcollection.Utils;

import android.content.Context;
import android.view.animation.Animation;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;

/**
 * Created by liuchuan on 2015/5/6.
 * 创建带默认设置的Xutils各个模块
 */
public class XutilsGetUtils {
    /**
     * 获得一个带默认缓存路径的BitmapUtils
     *
     * @param context
     * @return
     */
    public static BitmapUtils getBitmapUtils(Context context) {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int mCacheSize = maxMemory / 8;
        return new BitmapUtils(context, Settings.getImageCachePath(context), mCacheSize);
    }

    /**
     * 获得一个带默认缓存路径和默认加载图片的的BitmapUtils
     *
     * @param context
     * @param drawableid
     * @return
     */
    public static BitmapUtils getBitmapUtils(Context context, int drawableid) {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int mCacheSize = maxMemory / 8;
        BitmapUtils bitmapUtils = new BitmapUtils(context, Settings.getImageCachePath(context), mCacheSize);
        bitmapUtils.configDefaultLoadingImage(context.getResources().getDrawable(drawableid));
        return bitmapUtils;
    }

    /**
     * 获得一个带默认缓存路径和默认加载图片的的BitmapUtils 带加载失败的图片 加载动画可选
     *
     * @param context
     * @param drawableid
     * @param failedDrawableid
     * @param loadAnimation    可以为null
     * @return
     */
    public static BitmapUtils getBitmapUtils(Context context, int drawableid, int failedDrawableid, Animation loadAnimation) {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int mCacheSize = maxMemory / 8;
        BitmapUtils bitmapUtils = new BitmapUtils(context, Settings.getImageCachePath(context), mCacheSize);
        bitmapUtils.configDefaultLoadingImage(context.getResources().getDrawable(drawableid));
        bitmapUtils.configDefaultLoadFailedImage(context.getResources().getDrawable(failedDrawableid));
        if (loadAnimation != null)
            bitmapUtils.configDefaultImageLoadAnimation(loadAnimation);
        return bitmapUtils;
    }

    public static HttpUtils getHttpUtils() {
        return new HttpUtils(Settings.HTTPTIMEOUT);
    }

    public static HttpUtils getHttpUtils(int timeout) {
        return new HttpUtils(timeout);
    }

    public static DbUtils getDbUtils(Context context) {
        DbUtils dbUtils = DbUtils.create(context, "jyt.db", Settings.DBVERSION_CURRENT, new DbUtils.DbUpgradeListener() {
            @Override
            public void onUpgrade(DbUtils dbUtils, int oldVersion, int newVersion) {
            }
        });
        dbUtils.configDebug(true);
        return dbUtils;
    }

}
