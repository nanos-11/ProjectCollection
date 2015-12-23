package com.nan.projectcollection.Utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * 跟网络相关的工具类
 */
public class NetUtils {
    private NetUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {

        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (null != connectivity) {

                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (null != info && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (cm == null)
                return false;
            if (cm.getActiveNetworkInfo() == null) {
                return false;
            }
            return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivity(intent);
    }


    public static void checkNetAndNotice(Context context) {
        if (!checkNet(context)) {
            Toast.makeText(context, "亲，网络忘了打开了", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 检查网络工具
     *
     * @return
     */
    public static boolean checkNet(Context context) {
        // 判断WIFI连接
        boolean isWIFI = isWIFIConnectivity(context);
        // 判断是APNList连接
        boolean isMobile = isMobileConnectivity(context);

        if (!isWIFI && !isMobile) {
            // 提示用配置网络
            return false;
        }

        // 如果Mobile连接
        if (isMobile) {
            // 判断wap还是net，如果是wap，设置ip和端口
            // 读取当前处于连接的apn信息，ip和端口是否有值
            // 读取操作
            readAPN(context);
        }

        return true;
    }

    /**
     * 读取操作——APN
     *
     * @param context
     */
    public static String readAPN(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        // 如果是wap网络,则设置代理
        if (networkInfo.getExtraInfo().toLowerCase().contains("wap")) {
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String IMSI = telephonyManager.getSubscriberId();
            // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
            if (!TextUtil.isEmpty(IMSI) && IMSI.startsWith("46003")) {
                return "10.0.0.200";
            } else {
                return "10.0.0.172";
            }
        }
        return null;
    }

    /**
     * 判断移动网络连接状态
     *
     * @param context
     * @return
     */
    public static boolean isMobileConnectivity(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (networkInfo != null) {
            return networkInfo.isConnected();
        }

        return false;
    }

    /**
     * 判断WIFI连接状态
     *
     * @param context
     * @return
     */
    private static boolean isWIFIConnectivity(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (networkInfo != null) {
            return networkInfo.isConnected();
        }

        return false;
    }

}
