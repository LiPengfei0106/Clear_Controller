package com.cleartv.controller.common.downloader;

import android.content.Context;

public class WolfDownloader {
    FileDownloader fileDownloader;

    public WolfDownloader(Context mContext, DownloaderConfig config) {
        this.fileDownloader = new FileDownloader(mContext);
        this.fileDownloader.setConfig(config);
    }

    public void readHistory(HistoryCallback historyCallback) {
        this.fileDownloader.readHistory(historyCallback);
    }

    public void startDownload() {
        fileDownloader.start();
    }

    public void pauseDownload() {
        fileDownloader.pause();
    }

    public void restartDownload() {
        fileDownloader.restart();
    }

    public void stopDownload() {
        fileDownloader.stop();
    }

    public void exitDownload() {
        fileDownloader.exit();
    }

}