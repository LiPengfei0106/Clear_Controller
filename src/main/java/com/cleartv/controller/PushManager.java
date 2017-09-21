package com.cleartv.controller;

import android.app.Application;
import android.content.Intent;

import com.cleartv.controller.common.util.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

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

	public static void setAlias(String alias){
		if(!hasRegister)
			return;
//		JPushInterface.setAlias(mContext,0,alias);
		JPushInterface.setAlias(mContext, alias, new TagAliasCallback() {
			@Override
			public void gotResult(int i, String s, Set<String> set) {
				Logger.i(TAG,"gotResult:"+i);
				if(i == 0){
					Intent broadcast = new Intent("com.cleartv.controller.CONTROLLER");
					broadcast.putExtra("alias",s);
					mContext.sendBroadcast(broadcast);
				}
			}
		});
	}

	public static void setTags(List<String> tags) {
		if(!hasRegister)
			return;
		JPushInterface.setTags(mContext,0,new HashSet<>(tags));
	}
}
