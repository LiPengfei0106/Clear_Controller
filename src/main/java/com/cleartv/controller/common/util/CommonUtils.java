package com.cleartv.controller.common.util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.cleartv.controller.MyApplication;
import com.cleartv.controller.common.downloader.DownloadProgressListener;
import com.cleartv.controller.common.downloader.DownloaderConfig;
import com.cleartv.controller.common.downloader.WolfDownloader;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Lee on 2017/9/19.
 */

public class CommonUtils {
    private static final String TAG = "CommonUtils";

    public static void installApk(Context context, int versionCode, String packageName, final String apkUrl){
        if(versionCode > 0 && !StringUtils.isEmpty(packageName) && versionCode <= PackageUtils.getAppVersionCode(context,packageName)){
            return;
        }
        Logger.d(TAG,"installApk:"+apkUrl);
//        File downloadDir = new File(Environment.getExternalStorageDirectory(), "ControllerDownload");
        File downloadDir = new File(context.getCacheDir(), "download");
        if(!downloadDir.exists()){
            downloadDir.mkdirs();
        }
        try {
            String command = "chmod 777 " + downloadDir.getAbsolutePath();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (Exception e) {
            Logger.d(TAG, "[" + e.getMessage() + "]");
        }
//        downloadDir = new File(context.getExternalCacheDir(), "download");
        Logger.d(TAG,"downloadDir:"+downloadDir.getAbsolutePath());
//        downloadDir = new File(context.getFilesDir(), "download");
//        Logger.d(TAG,"downloadDir:"+downloadDir.getAbsolutePath());
//        downloadDir = new File(context.getCacheDir(), "download");
//        Logger.d(TAG,"downloadDir:"+downloadDir.getAbsolutePath());
//        downloadDir = new File(context.getExternalFilesDir(null), "download");
//        Logger.d(TAG,"downloadDir:"+downloadDir.getAbsolutePath());
        WolfDownloader downloader = new DownloaderConfig()
                .setDownloadUrl(apkUrl)
                .setSaveDir(downloadDir)
                .setThreadNum(3)
                .setDownloadListener(new DownloadProgressListener() {
                    @Override
                    public void onDownloadTotalSize(int totalSize) {
                        Logger.d(TAG,"onDownloadTotalSize:"+totalSize);
                    }

                    @Override
                    public void updateDownloadProgress(int size, float percent, float speed) {
                        Logger.d(TAG,"updateDownloadProgress: size("+size+");percent("+percent+");speed("+speed+")");
                    }

                    @Override
                    public void onDownloadSuccess(String apkPath) {
                        Logger.d(TAG,"onDownloadSuccess: "+apkPath);
                        try {
                            String command = "chmod 777 " + apkPath;
                            Runtime runtime = Runtime.getRuntime();
                            runtime.exec(command);
                        } catch (Exception e) {
                            Logger.d(TAG, "[" + e.getMessage() + "]");
                        }
                        PackageUtils.installSilent(apkPath);
                    }

                    @Override
                    public void onDownloadFailed() {
                        Logger.d(TAG,"onDownloadFailed: "+apkUrl);
                    }

                    @Override
                    public void onPauseDownload() {

                    }

                    @Override
                    public void onStopDownload() {

                    }
                }).buildWolf(context);
        downloader.startDownload();
    }

    public static String getDeviceID(){
        String ANDROID_ID = Settings.System.getString(MyApplication.mApplication.getContentResolver(), Settings.System.ANDROID_ID);
        String deviceID = Build.SERIAL+"_"+ANDROID_ID+"_"+NetWorkUtils.getMacAddress();
        Logger.d("CommonUtils",deviceID);
        return md5(deviceID);
    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
