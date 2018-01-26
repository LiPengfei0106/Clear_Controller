package com.cleartv.controller.JMessage;

import cn.jpush.im.android.api.event.MessageEvent;

/**
 * Created by jjy on 2018/1/26.
 *
 * 接受消息的接口。如果不需要的话可以删除
 */

public interface EventReceiver {
    /**
     * 当收到消息之后需要在主线程进行的操作
     *
     * @param event 消息
     */
    void onEventMainThread(MessageEvent event);

    /**
     * 当收到消息之后需要在子线程做的操作
     *
     * @param event 消息
     */
    void onEvent(MessageEvent event);
}
