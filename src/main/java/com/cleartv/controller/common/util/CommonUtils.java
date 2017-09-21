package com.cleartv.controller.common.util;

import android.content.Context;

import com.cleartv.controller.common.downloader.DownloadProgressListener;
import com.cleartv.controller.common.downloader.DownloaderConfig;
import com.cleartv.controller.common.downloader.WolfDownloader;

import java.io.File;

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
        File downloadDir = new File(context.getExternalCacheDir(), "download");
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
}
