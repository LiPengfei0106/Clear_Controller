package com.cleartv.controller.common.util;

import android.util.Log;

/**
 * Created by Lee on 2017/4/13.
 */

public class Logger {

    //设为false关闭日志
    private static final boolean LOG_ENABLE = true;
    private static final String LOG_PREFIX = "Controller_";

    public static void i(String tag, String msg){
        if (LOG_ENABLE){
            Log.i(LOG_PREFIX + tag, msg);
        }
    }
    public static void v(String tag, String msg){
        if (LOG_ENABLE){
            Log.v(LOG_PREFIX + tag, msg);
        }
    }
    public static void d(String tag, String msg){
        if (LOG_ENABLE){
            Log.d(LOG_PREFIX + tag, msg);
        }
    }
    public static void w(String tag, String msg){
        if (LOG_ENABLE){
            Log.w(LOG_PREFIX + tag, msg);
        }
    }
    public static void e(String tag, String msg){
        if (LOG_ENABLE){
            Log.e(LOG_PREFIX + tag, msg);
        }
    }

}
