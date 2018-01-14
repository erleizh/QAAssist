package com.lll.auxiliary.utils;

/**
 * Created by lll on 2018/1/12.
 * 日志工具类
 */
public class Log {

    public static void i(String tag, String msg) {
        android.util.Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        android.util.Log.w(tag, msg);
    }

    public static void d(String tag, String msg) {
        android.util.Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        android.util.Log.e(tag, msg);
    }
}
