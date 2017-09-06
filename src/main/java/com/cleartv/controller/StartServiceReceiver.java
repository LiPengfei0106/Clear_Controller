package com.cleartv.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, ControllerService.class);
        context.startService(service);
        Log.v("TAG", "ControllerService服务启动.....");
    }

}