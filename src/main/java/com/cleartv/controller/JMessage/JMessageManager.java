package com.cleartv.controller.JMessage;

import android.app.Application;

import com.cleartv.controller.ControllerManager;
import com.cleartv.controller.common.util.Logger;

import java.util.HashSet;
import java.util.List;

import cn.jpush.im.android.api.ChatRoomManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.RequestCallback;
import cn.jpush.im.android.api.model.ChatRoomInfo;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by Lee on 2018/1/23.
 */

public class JMessageManager {

    private static final String TAG = "JMessageManager";

    private static Conversation sConversation; // 群聊Conversation
    private static long sConversationId;// 群聊ID

    private static Conversation conversation;
    private static ChatRoomInfo chatRoomInfo;

    public static void init(Application application){
        JMessageClient.init(application);

        JMessageClient.register(ControllerManager.getDeviceUid(), "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {

            }
        });

        JMessageClient.login(ControllerManager.getDeviceUid(), "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {

            }
        });
    }

    public static void createRoom(long roomID){
        conversation = Conversation.createChatRoomConversation(roomID);
        ChatRoomManager.enterChatRoom(roomID, new RequestCallback<Conversation>() {
            @Override
            public void gotResult(int i, String s, Conversation conversation) {

            }
        });
        HashSet<Long> rooms = new HashSet<Long>();
        rooms.add(roomID);
        ChatRoomManager.getChatRoomInfos(rooms, new RequestCallback<List<ChatRoomInfo>>() {
            @Override
            public void gotResult(int i, String s, List<ChatRoomInfo> chatRoomInfos) {
                if(null != chatRoomInfos && chatRoomInfos.size()>0)
                    chatRoomInfo = chatRoomInfos.get(0);
            }
        });

    }

    public static void setRoomInfo(String json){

    }

    /**
     * 创建聊天室
     *
     * @param id 聊天室id
     */
    public static void createChatGroup(long id) {
        Logger.d(TAG, "创建聊天群 " + id);
        sConversation = Conversation.createGroupConversation(id);
        sConversationId = id;
    }

    /**
     * vod那边有操作，需要更新状态
     *
     * @param statusJson 传递过来的Json
     */
    public static void notifyStatusChanged(String statusJson) {
        Logger.d(TAG, "notify status: " + statusJson);
        if (sConversation == null) {
            return;
        }
        JMessageClient.enterGroupConversation(sConversationId);
        JMessageClient.sendMessage(JMessageClient.createGroupTextMessage(
                sConversationId, statusJson
        ));
    }
}
