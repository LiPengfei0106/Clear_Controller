package com.cleartv.controller.common.downloader;

public interface DownloadProgressListener {
    void onDownloadTotalSize(int totalSize);

    /**
     * Real-time update downloading progress
     *
     * @param size    downloading progress(Byte)
     * @param percent downloading percent(%)
     * @param speed   downloading speed(KB/S)
     */
    void updateDownloadProgress(int size, float percent, float speed);

    void onDownloadSuccess(String apkPath);

    void onDownloadFailed();

    void onPauseDownload();

    void onStopDownload();
}
