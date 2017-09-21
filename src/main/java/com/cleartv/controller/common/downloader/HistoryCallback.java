package com.cleartv.controller.common.downloader;

public interface HistoryCallback {
    void onReadHistory(int downloadLength, int fileSize);
}