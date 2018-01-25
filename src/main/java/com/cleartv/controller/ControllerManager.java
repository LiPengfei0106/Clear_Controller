package com.cleartv.controller;

import android.content.Intent;
import android.os.RemoteException;

import com.alibaba.fastjson.JSON;
import com.cleartv.controller.common.util.CommonUtils;
import com.cleartv.controller.common.util.KeyEventUtil;
import com.cleartv.controller.common.util.Logger;
import com.cleartv.controller.common.util.PackageUtils;
import com.cleartv.controller.common.util.ShellUtils;
import com.cleartv.controller.common.util.SignUtil;
import com.cleartv.controller.entity.CurrentStatus;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;

/**
 * Created by Lee on 2017/12/15.
 */

public class ControllerManager {
    private static final String TAG = "ControllerManager";
    private static Conversation sConversation;
    private static long sConversationId;

    /**
     * 处理推送过来的消息
     *
     * @param content 消息内容
     */
    public static void HandleMsg(String content) {
        Logger.i(TAG, content);
        try {
            JSONObject object = new JSONObject(content);
            if (object.has("package")) {
                String packageName = object.getString("package");
                Logger.d(TAG, packageName + ".CONTROLLER");
                sendToOtherPackage(packageName, content);
            } else if (object.has("packageName")) {
                String packageName = object.getString("packageName");
                Logger.d(TAG, packageName + ".CONTROLLER");
                sendToOtherPackage(packageName, content);
            } else if (object.has("action") && object.has("data")) {
                String action = object.getString("action");
                JSONObject data = object.getJSONObject("data");
                switch (action) {
                    case "installApk":
                        int versionCode = data.getInt("versionCode");
                        String packageName = data.getString("packageName");
                        if (data.has("apkUrl")) {
                            String apkUrl = data.getString("apkUrl");
                            if (!data.has("signMD5")) {
                                CommonUtils.installApk(MyApplication.mApplication, versionCode, packageName, apkUrl);
                            } else {
                                String signMD5 = data.getString("signMD5");
                                if (signMD5.equalsIgnoreCase(SignUtil.getSignMd5Str(MyApplication.mApplication))) {
                                    CommonUtils.installApk(MyApplication.mApplication, versionCode, packageName, apkUrl);
                                }
                            }
                        } else if (data.has("filePath")) {
                            PackageUtils.installSilent(data.getString("filePath"));
                        }
                        break;
                    case "shell":
                        ShellUtils.execCommand(data.getString("command"), false, true);
                        break;
                    case "keyEvent":
                        KeyEventUtil.sendKeyUpDown(data.getInt("keyCode"));
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void sendToOtherPackage(String packageName, String content) {
        Intent broadcast = new Intent(packageName + ".CONTROLLER");
        broadcast.putExtra("content", content);
        MyApplication.mApplication.sendBroadcast(broadcast);
    }

    public static String getDeviceUid() {
        return CommonUtils.getDeviceID();
    }
}
