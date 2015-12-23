package com.nan.projectcollection.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;


public class UIUtils {


    /**
     * 动态替换文本并改变文本颜色
     * 仅限制一种颜色
     */
    public static SpannableStringBuilder getSpannableStringBuilder(Context context, int resStrId, int resColorId,
                                                                   Object... args) {
        return getSpannableStringBuilder(context, context.getString(resStrId, args), resColorId, args);
    }

    /**
     * 动态替换文本并改变文本颜色
     * 仅限制一种颜色
     */
    public static SpannableStringBuilder getSpannableStringBuilder(Context context, String resStr, int resColorId,
                                                                   Object... args) {

        SpannableStringBuilder spanStr = new SpannableStringBuilder(resStr);
        int i = -1;
        for (Object o : args) {
            if (o instanceof String) {
                String arg = (String) o;
                i = resStr.indexOf(arg, i + 1);
                spanStr.setSpan(new ForegroundColorSpan(context.getResources().getColor(resColorId)), i,
                        i + arg.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        return spanStr;
    }



    /**
     * 扩大view的点击区域
     *
     * @param view
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    public static void expandViewTouchDelegate(final View view, final int top,
                                               final int bottom, final int left, final int right) {

        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                view.setEnabled(true);
                view.getHitRect(bounds);

                bounds.top -= top;
                bounds.bottom += bottom;
                bounds.left -= left;
                bounds.right += right;

                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }

    /**
     * 跳转到新activity
     *
     * @param activity  老activity，用于调起startActivity()方法
     * @param intent    跳到新activity的intent
     * @param enterAnim 新activity的进入动画
     * @param exitAnim  老activity的退出动画
     */
    public static void startActivityWithAnim(Activity activity, Intent intent, int enterAnim, int exitAnim) {
        activity.startActivity(intent);
        activity.overridePendingTransition(enterAnim, exitAnim);
        activity = null;
    }

    /**
     * 结束activity
     *
     * @param activity  老activity，用于调起finish()方法
     * @param enterAnim 新activity的进入动画
     * @param exitAnim  老activity的退出动画
     */
    public static void finishActivityWithAnim(Activity activity, int enterAnim, int exitAnim) {
        activity.finish();
        activity.overridePendingTransition(enterAnim, exitAnim);
        activity = null;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    public static void showDialog(Dialog dialog) {
        if (dialog != null && !dialog.isShowing())
            dialog.show();
    }

    public static void dismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }


    // 屏幕高度
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getScreenHeight(Activity context) {

        Display display = context.getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            display.getSize(size);
            return size.y;
        }
        return display.getHeight();
    }

    // 屏幕宽度
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getScreenWidth(Activity context) {

        Display display = context.getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
        return display.getWidth();
    }

    // dp to px
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    // px to dp
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float display(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return dm.density;
    }

    /**
     * 获得最小移动距离 后控件移动方向
     *
     * @param context
     * @return
     */
    public static int getTouchSlop(Context context) {
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        return configuration.getScaledTouchSlop();
    }

}
