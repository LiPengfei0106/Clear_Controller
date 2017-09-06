package com.cleartv.controller;

import android.app.Instrumentation;

/**
 * Created by Lee on 2017/9/6.
 */

public class CommonUtils {

    private static Instrumentation mInstrumentation;

    public static void sendKeyUpDown(final int keyCode){
        if (mInstrumentation == null) {
            mInstrumentation = new Instrumentation();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                mInstrumentation.sendKeyDownUpSync(keyCode);
            }
        }).start();
    }

}
