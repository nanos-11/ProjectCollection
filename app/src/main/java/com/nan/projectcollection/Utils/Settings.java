package com.nan.projectcollection.Utils;

import android.content.Context;

/**
 * 全局的配置
 */
public class Settings {
    //本地存储用户下载地区数据库的相关信息
    public static final String SP_NAME_DB_AREA = "update_area_db_sp";
    public static final String SP_NAME_SAVE_USER = "save_user_info_sp";
    public static final String NAME_FOR_DOWNLOADVIDEO = ".ttxs";
    //域名
//    public static String DOMAINNAME = AppEnv.DOMAINNAME;
    public static String DOMAINNAME_TEST = "http://172.16.10.133/";
    //支付回调
//    public static final String PAYFORALIPAY = DOMAINNAME.equals(AppEnv.DOMAINNAME_TEST)
//            ? "http://119.57.93.67:88/service/ttstv2/payController/notify/alipay"
//            : DOMAINNAME + "service/ttstv2/payController/notify/alipay";
//    public static final String PAYFORWXPAY = DOMAINNAME.equals(AppEnv.DOMAINNAME_TEST)
//            ? "http://119.57.93.67:88/service/ttstv2/payController/notify/wxpay"
//            : DOMAINNAME + "service/ttstv2/payController/notify/wxpay";

    /**
     * 天天向上相关参数
     //     */
//    public static final String PACKAGE_NAME = "com.jiyoutang.dailyup";
//    public static final int VERSIONCODE_LOW = 11;
//    public static final String DOWNLOAD_APK_URL = DOMAINNAME + "source/upgrade/tiantianxiangshangApk/ttxs-ttst-release.apk";
//    public static final String TEACHER_REGIST_URL = DOMAINNAME + "html/mobile/product/msrz.html";
//    public static final String MY_MALL_URL = "http://kdt.im/VNqmFgDo6";
    /**
     * 数据库版本
     */
    public static final int DBVERSION_ONE = 1;
    public static final int DBVERSION_TWO = 2;
    public static final int DBVERSION_THREE = 3;
    public static final int DBVERSION_FOUR = 4;
    public static final int DBVERSION_CURRENT = 5;
    /**
     * 一般的网络超时
     */
    public static final int HTTPTIMEOUT = 1000 * 30;
    /**
     * 检查数据学校数据库的时间间隔 30天
     */
    public static final long CHEACK_SCHOOL_DB_TIME = 1000 * 60 * 60 * 24 * 30;
    //errorecode
    public static final int HTTPTERROR_NORMAL = 3000;
    public static final int HTTPTERROR_APIUNNORMAL = 3001;
    //登录注册状态码
    public static final int HTTPTERROR_ERRORNAME = 3103;
    public static final int HTTPTERROR_ERRORPASSWORD = 3102;
    public static final int HTTPTERROR_ERRORCODE = 3102;
    public static final int HTTPTERROR_NOUSERNAME = 3101;
    //注册状态码
    public static final int HTTPTERROR_CODE_ERROR = 3106;
    public static final int HTTPTERROR_CODE_TIMEOUT = 3105;
    public static final int HTTPTERROR_USER_HAS_REGIST = 3103;
    //不能重复发送
    public static final int HTTPTERROR_CAT_NOT_RESEND = 3108;
    //发送失败
    public static final int HTTPTERROR_SEND_ERROR = 3107;
    //象牙购买成功
    public static final int HTTPTERROR_BUYWITHXYSUCESS = 3300;
    //订单成功
    public static final int HTTPTERROR_ORDERSUCESS = 3302;
    //订单失败
    public static final int HTTPTERROR_ORDERFAIL = 3303;
    //删除购买充值记录
    public static final int HTTPERROR_DELETE_ERROR = 3009;
    /**
     * 删除订单成功
     */
    public static final int HTTPTERROR_3304 = 3304;
    /**
     * 订单删除失败
     */
    public static final int HTTPTERROR_3305 = 3305;
    /**
     * 请求内容非法
     */
    public static final int HTTPTERROR_3201 = 3201;
    /**
     * 内容不存在
     */
    public static final int HTTPTERROR_3202 = 3202;
    /**
     * 密码最短数字
     */
    public static final int PASSWORD_LENGTH = 6;
    /**
     * 手机长度
     */
    public static final int PHONE_LENGTH = 11;
    public static final String DB_NAME_AREA = "area.db";
    /**
     * App的source 20代表Android 30代表IOS
     */
    public static final String SOURCE = "20";
    /**
     * sourceApp 0 网络教辅 ；1 天天象上 ；2 智能教辅； 3 名师辅导； 4 名校好卷； 5 错题笔记； 6 天天扫题
     */
    public static final String SOURCEAPP = "6";
    /**
     * 获取验证码部分 sign(模块 10注册验证 11找回密码 12绑定手机/邮箱
     */
    public static final String CODE_SING_REGISTER = "10";
    /**
     * 获取验证码部分 sign(模块 10注册验证 11找回密码 12绑定手机/邮箱
     */
    public static final String CODE_SING_FIND_PASSWORD = "11";
    /**
     * 获取验证码部分 sign(模块 10注册验证 11找回密码 12绑定手机/邮箱
     */
    public static final String CODE_SING_BIND_PHONE_OR_EMAIL = "12";
    /**
     * 题目CODE长度
     */
    public static final int ISSUECODE_LENGTH = 4;

    /**
     * 支付 1 成功 0失败 2取消支付订单
     */
    public static final int RESULT_PAY_CANCEL = 2;
    public static final int RESULT_PAY_SUCCESS = 1;
    public static final int RESULT_PAY_FAILED = 0;

    /**
     * 版本是否更新
     */
    public static boolean hasUpdate = false;

    /**
     * monkey使用到的code
     */
    public static String CODE_FORMONKEY[] = {"922u", "9237", "2858", "295y", "2BM8", "289W", "w26q",
            "w22m", "2bf7", "29ee", "29Gx", "2cy4", "2cwq", "xxxx", "z2uu", "z2um", "z2zj", "z2cv"};

    /**
     * 版本是否开启monkey模式
     */
    public static boolean isMonkeyMode = false;

//	public static String getTTXSDomainName() {
//        if (DOMAINNAME.equals(AppEnv.DOMAINNAME_TEST)) {
//            return DOMAINNAME;
//        } else if (DOMAINNAME.equals(AppEnv.DOMAINNAME_TWWW)) {
//            return "http://tttxs.daydays.com/";
//        } else if (DOMAINNAME.equals(AppEnv.DOMAINNAME_COM)) {
//            return "http://ttxs.daydays.com/";
//        } else {
//            return DOMAINNAME;
//        }
//    }

    public static String getDownloadPath(Context context) {
        if (context == null) {
            return "";
        } else {
            return context.getExternalFilesDir(null) + "/download/";
        }
    }

    public static String getImageCachePath(Context context) {
        if (context == null) {
            return "";
        } else {
            return context.getExternalFilesDir(null) + "/cache/";
        }
    }

    public static String getDatabasePath(Context context) {
        if (context == null) {
            return "";
        } else {
            return context.getExternalFilesDir(null) + "/db/";
        }
    }

    public static String getCrashPath(Context context) {
        if (context == null) {
            return "";
        } else {
            return context.getExternalFilesDir(null) + "/crash/";
        }
    }
}
