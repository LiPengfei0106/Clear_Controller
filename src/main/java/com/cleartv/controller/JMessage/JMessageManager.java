package com.cleartv.controller.JMessage;

import android.app.Application;

import com.cleartv.controller.ControllerManager;
import com.cleartv.controller.common.util.Logger;
import com.cleartv.controller.entity.CurrentStatus;
import com.google.gson.jpush.Gson;
import com.google.gson.jpush.JsonObject;

import java.util.HashSet;
import java.util.List;

import cn.jpush.im.android.api.ChatRoomManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.CreateGroupCallback;
import cn.jpush.im.android.api.callback.RequestCallback;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.ChatRoomInfo;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by Lee on 2018/1/23.
 * <p>
 * 本机流程：
 * 1.注册；
 * 2.登录；
 * 3.创建群组；
 * 4.加入群组；
 * <p>
 * 远程登陆流程：
 * 1.加入群组；
 */

public class JMessageManager implements EventReceiver {

    private static final String TAG = "JMessageManager";
    public static JMessageManager instance;

    private static Conversation sConversation; // 群聊Conversation
    private static long sConversationId;// 群聊ID

    private static Conversation conversation;
    private static ChatRoomInfo chatRoomInfo;

    private long mGroupId;
    private Conversation mConv;

    public JMessageManager() {
        JMessageClient.registerEventReceiver(this);
    }

    public static JMessageManager get() {
        if (instance == null) {
            instance = new JMessageManager();
        }
        return instance;
    }

    public static void init(Application application) {
        JMessageClient.init(application);
        JMessageManager.get().login();
    }

    private void login() {
        JMessageClient.login(ControllerManager.getDeviceUid(), "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Logger.d(TAG, "login: i = " + i + ", s = " + s);
                if (i != 0) {
                    // 登陆失败
                    register();
                } else {
                    // 登录成功，创建群组
                    createChatGroup();
                }
            }
        });
    }

    private void register() {
        JMessageClient.register(ControllerManager.getDeviceUid(), "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Logger.d(TAG, "register: i = " + i + ", s = " + s);
                if (i == 0) {
                    //注册成功
                    login();
                }
            }
        });
    }


    /**
     * 创建群组
     */
    public void createChatGroup() {
//        JMessageClient.createPublicGroup("公开群组", "公开群组简介", new CreateGroupCallback() {
//            @Override
//            public void gotResult(int i, String s, long l) {
//
//            }
//        });fixme 公开群组还是私有群组
        JMessageClient.createGroup("群组名称", "群组简介", new CreateGroupCallback() {
            @Override
            public void gotResult(int i, String s, long id) {
                mGroupId = id;
                mConv = Conversation.createGroupConversation(id);
            }
        });
    }

    /**
     * vod那边有操作，传递消息过来，需要更新状态
     *
     * @param statusJson 传递过来的Json
     */
    public void notifyStatusChanged(final String statusJson) {
        Logger.d(TAG, "notify status: " + statusJson);
        CurrentStatus status = new Gson().fromJson(statusJson, CurrentStatus.class);

        switch (status.type) {
            case "media":
                onMediaEvent(statusJson);
                break;
            case "member":
                onMemberEvent(statusJson);
                break;
        }
    }

    public void onMediaEvent(String status) {
        JMessageClient.sendMessage(mConv.createSendTextMessage(status));
    }

    public void onMemberEvent(String status) {
        // TODO: 2018/1/26  添加群成员
    }

    @Override
    public void onEventMainThread(MessageEvent event) {
        // 暂时不用
    }

    @Override
    public void onEvent(MessageEvent event) {
        // 暂时不用
    }
}
