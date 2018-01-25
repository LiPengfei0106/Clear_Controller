package com.cleartv.controller.entity;

/**
 * Created by jjy on 2018/1/24.
 * 当前状态，传递到Controller中。
 */

public class CurrentStatus {
    public int event;// 事件类型，如播放，暂停等
    public String info;// 额外信息
    public String viewName;// 当前模块名称
    public String viewType;// 当前ViewType
    public String videoName;// 当前播放的媒体名称
    public String videoUrl;// 当前播放的媒体url
    public String videoProgress;// 当前播放的媒体进度
    public String videoTotalLength;// 当前播放的媒体总长度
    public String director;// 导演，如果媒体是电影
    public String actor;// 演员，如果媒体是电影
    public String musician;// 音乐家，如果媒体是音乐

    /**
     * Created by jjy on 2018/1/24.
     */

    public static class StatusConstant {
        public static int VIDEO_PLAY = 100;
        public static int VIDEO_PAUSE = 101;
        public static int VIDEO_RESUME = 102;
        public static int VIDEO_STOP = 103;
        public static int VIDEO_PLAY_ADV = 104;
        public static int VIDEO_SHOW_QR = 105;
        public static int VIDEO_SHOW_TIP = 106;
    }
}
