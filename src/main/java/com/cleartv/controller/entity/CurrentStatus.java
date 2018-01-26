package com.cleartv.controller.entity;

/**
 * Created by jjy on 2018/1/24.
 * 当前状态，传递到Controller中。
 */

public class CurrentStatus {
    public String type;// 消息类型
    public Object content;// 消息内容

    public CurrentStatus(String type, Object content) {
        this.type = type;
        this.content = content;
    }

    public static class MediaContent {
        public String state;// 当前媒体的播放状态
        public String name;// 当前播放的媒体名称
        public String url;// 当前播放的媒体url
        public String picUrl;// 图片地址
        public String videoProgress;// 当前播放的媒体进度
        public String videoTotalLength;// 当前播放的媒体总长度
        public String director;// 导演，如果媒体是电影
        public String actor;// 演员，如果媒体是电影
        public String musician;// 音乐家，如果媒体是音乐

        public  enum MediaType {
            Music, Movie, Live
        }
    }

    public static class MemberContent {

        /**
         * fixme ID?
         * nickName : app.globalData.userInfo.nickName
         * headImgUrl : app.globalData.userInfo.avatarUrl
         * sex : app.globalData.userInfo.gender
         * province : app.globalData.userInfo.province
         * country : app.globalData.userInfo.country
         * city : app.globalData.userInfo.city
         * language : app.globalData.userInfo.language
         */
        public String nickName;
        public String headImgUrl;
        public int sex;
        public String province;
        public String country;
        public String city;
        public String language;
    }

    public static final String TYPE_MEDIA = "media";
    public static final String TYPE_MEMBER = "member";
    public static final String TYPE_DEFAULT = "default";
}
