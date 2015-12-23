package com.nan.projectcollection.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * 判断网络类型
 *
 * @author Administrator
 */
@SuppressLint("DefaultLocale")
public class NetUtil {
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
    private static boolean isMobileConnectivity(Context context) {
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