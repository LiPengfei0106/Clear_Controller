package com.cleartv.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cleartv.controller.common.util.CommonUtils;
import com.cleartv.controller.common.util.KeyEventUtil;
import com.cleartv.controller.common.util.PackageUtils;
import com.cleartv.controller.common.util.ShellUtils;

public class ControllerReceiver extends BroadcastReceiver {

    /**
     * <action android:name="com.cleartv.controller.intent.SHELL"/>
     * <action android:name="com.cleartv.controller.intent.KEYUPDOWN"/>
     * <action android:name="com.cleartv.controller.intent.INSTALLAPK"/>
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()){
            case "com.cleartv.controller.intent.SHELL":
                if(intent.hasExtra("command"))
                    ShellUtils.execCommand(intent.getStringExtra("command"),ShellUtils.checkRootPermission());
                else if(intent.hasExtra("commands"))
                    ShellUtils.execCommand(intent.getStringArrayExtra("commands"),ShellUtils.checkRootPermission());
                break;
            case "com.cleartv.controller.intent.KEYUPDOWN":
                if(intent.hasExtra("keyCode"))
                    KeyEventUtil.sendKeyUpDown(intent.getIntExtra("keyCode",-1));
                break;
            case "com.cleartv.controller.intent.INSTALLAPK":
                if(intent.hasExtra("apkPath"))
                    PackageUtils.installSilent(intent.getStringExtra("apkPath"));
                else if(intent.hasExtra("apkUrl"))
                    CommonUtils.installApk(context,intent.getIntExtra("versionCode",-1),intent.getStringExtra("packageName"),intent.getStringExtra("apkUrl"));
                break;
        }
    }
}
