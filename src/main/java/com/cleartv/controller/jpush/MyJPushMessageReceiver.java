package com.cleartv.controller.jpush;

import android.content.Context;
import android.content.Intent;

import com.cleartv.controller.common.util.Logger;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * 自定义JPush message 接收器,包括操作tag/alias的结果返回(仅仅包含tag/alias新接口部分)
 */
public class MyJPushMessageReceiver extends JPushMessageReceiver {

    private static final String TAG = "MyJPushMessageReceiver";

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        Logger.d(TAG, "onTagOperatorResult:" + jPushMessage.toString());
        super.onTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        Logger.d(TAG, "onCheckTagOperatorResult:" + jPushMessage.toString());
        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        Logger.d(TAG, "onAliasOperatorResult:" + jPushMessage.toString());
        super.onAliasOperatorResult(context, jPushMessage);
        if (jPushMessage.getErrorCode() == 0) {
            Intent broadcast = new Intent("com.cleartv.controller.CONTROLLER");
            broadcast.putExtra("alias", jPushMessage.getAlias());
            context.sendBroadcast(broadcast);
        }
    }
}
