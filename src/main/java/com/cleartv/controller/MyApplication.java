package com.cleartv.controller;

import android.app.Application;

/**
 * Created by Lee on 2017/9/4.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PushManager.registerPush(this);
    }
}
