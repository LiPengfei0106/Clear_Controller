package com.cleartv.controller;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

public class PushManager {
	
	private static final String TAG = "PushManager";
	private static Application mContext;
    private static boolean hasRegister = false;

	public static void registerPush(Application context){
		mContext = context;
		JPushInterface.setDebugMode(true);
        JPushInterface.init(mContext);

        hasRegister = true;
	}
	
	public static void stopPush(){
		if(!hasRegister)
			return;
		JPushInterface.stopPush(mContext);
	}
	
	public static void resumePush(){
		if(!hasRegister)
			return;
		JPushInterface.resumePush(mContext);
	}
	
	public static String getRegId(){
		if(!hasRegister)
			return null;
		return JPushInterface.getRegistrationID(mContext);
	}

}
