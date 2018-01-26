package com.cleartv.controller;

import android.app.Application;
import android.util.Log;

import com.cleartv.controller.JMessage.JMessageManager;
import com.cleartv.controller.jpush.PushManager;

/**
 * Created by Lee on 2017/9/4.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    public static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        PushManager.registerPush(this);

        Log.d(TAG, "onCreate");
        JMessageManager.init(this);
    }
}
